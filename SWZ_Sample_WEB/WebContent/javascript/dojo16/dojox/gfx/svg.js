dojo.provide("dojox.gfx.svg");

dojo.require("dojox.gfx._base");
dojo.require("dojox.gfx.shape");
dojo.require("dojox.gfx.path");

(function(){
	var d = dojo, g = dojox.gfx, gs = g.shape, svg = g.svg;
	svg.useSvgWeb = (typeof window.svgweb != "undefined");
	
	function _createElementNS(ns, nodeType){
		// summary:
		//		Internal helper to deal with creating elements that
		//		are namespaced.  Mainly to get SVG markup output
		//		working on IE.
		if(dojo.doc.createElementNS){
			return dojo.doc.createElementNS(ns,nodeType);
		}else{
			return dojo.doc.createElement(nodeType);
		}
	}
	
	function _createTextNode(text){
		if(svg.useSvgWeb){
			return dojo.doc.createTextNode(text, true);
		}else{
			return dojo.doc.createTextNode(text);
		}
	}
	
	function _createFragment(){
		if(svg.useSvgWeb){
			return dojo.doc.createDocumentFragment(true);
		}else{
			return dojo.doc.createDocumentFragment();
		}
	}

	svg.xmlns = {
		xlink: "http://www.w3.org/1999/xlink",
		svg:   "http://www.w3.org/2000/svg"
	};

	svg.getRef = function(name){
		// summary: returns a DOM Node specified by the name argument or null
		// name: String: an SVG external reference
		if(!name || name == "none") return null;
		if(name.match(/^url\(#.+\)$/)){
			return d.byId(name.slice(5, -1));	// Node
		}
		// alternative representation of a reference
		if(name.match(/^#dojoUnique\d+$/)){
			// we assume here that a reference was generated by dojox.gfx
			return d.byId(name.slice(1));	// Node
		}
		return null;	// Node
	};

	svg.dasharray = {
		solid:				"none",
		shortdash:			[4, 1],
		shortdot:			[1, 1],
		shortdashdot:		[4, 1, 1, 1],
		shortdashdotdot:	[4, 1, 1, 1, 1, 1],
		dot:				[1, 3],
		dash:				[4, 3],
		longdash:			[8, 3],
		dashdot:			[4, 3, 1, 3],
		longdashdot:		[8, 3, 1, 3],
		longdashdotdot:		[8, 3, 1, 3, 1, 3]
	};

	d.declare("dojox.gfx.svg.Shape", gs.Shape, {
		// summary: SVG-specific implementation of dojox.gfx.Shape methods

		setFill: function(fill){
			// summary: sets a fill object (SVG)
			// fill: Object: a fill object
			//	(see dojox.gfx.defaultLinearGradient,
			//	dojox.gfx.defaultRadialGradient,
			//	dojox.gfx.defaultPattern,
			//	or dojo.Color)

			if(!fill){
				// don't fill
				this.fillStyle = null;
				this.rawNode.setAttribute("fill", "none");
				this.rawNode.setAttribute("fill-opacity", 0);
				return this;
			}
			var f;
			// FIXME: slightly magical. We're using the outer scope's "f", but setting it later
			var setter = function(x){
					// we assume that we're executing in the scope of the node to mutate
					this.setAttribute(x, f[x].toFixed(8));
				};
			if(typeof(fill) == "object" && "type" in fill){
				// gradient
				switch(fill.type){
					case "linear":
						f = g.makeParameters(g.defaultLinearGradient, fill);
						var gradient = this._setFillObject(f, "linearGradient");
						d.forEach(["x1", "y1", "x2", "y2"], setter, gradient);
						break;
					case "radial":
						f = g.makeParameters(g.defaultRadialGradient, fill);
						var gradient = this._setFillObject(f, "radialGradient");
						d.forEach(["cx", "cy", "r"], setter, gradient);
						break;
					case "pattern":
						f = g.makeParameters(g.defaultPattern, fill);
						var pattern = this._setFillObject(f, "pattern");
						d.forEach(["x", "y", "width", "height"], setter, pattern);
						break;
				}
				this.fillStyle = f;
				return this;
			}
			// color object
			var f = g.normalizeColor(fill);
			this.fillStyle = f;
			this.rawNode.setAttribute("fill", f.toCss());
			this.rawNode.setAttribute("fill-opacity", f.a);
			this.rawNode.setAttribute("fill-rule", "evenodd");
			return this;	// self
		},

		setStroke: function(stroke){
			//	summary:
			//		sets a stroke object (SVG)
			//	stroke: Object
			// 		a stroke object (see dojox.gfx.defaultStroke)

			var rn = this.rawNode;
			if(!stroke){
				// don't stroke
				this.strokeStyle = null;
				rn.setAttribute("stroke", "none");
				rn.setAttribute("stroke-opacity", 0);
				return this;
			}
			// normalize the stroke
			if(typeof stroke == "string" || d.isArray(stroke) || stroke instanceof d.Color){
				stroke = { color: stroke };
			}
			var s = this.strokeStyle = g.makeParameters(g.defaultStroke, stroke);
			s.color = g.normalizeColor(s.color);
			// generate attributes
			if(s){
				rn.setAttribute("stroke", s.color.toCss());
				rn.setAttribute("stroke-opacity", s.color.a);
				rn.setAttribute("stroke-width",   s.width);
				rn.setAttribute("stroke-linecap", s.cap);
				if(typeof s.join == "number"){
					rn.setAttribute("stroke-linejoin",   "miter");
					rn.setAttribute("stroke-miterlimit", s.join);
				}else{
					rn.setAttribute("stroke-linejoin",   s.join);
				}
				var da = s.style.toLowerCase();
				if(da in svg.dasharray){
					da = svg.dasharray[da];
				}
				if(da instanceof Array){
					da = d._toArray(da);
					for(var i = 0; i < da.length; ++i){
						da[i] *= s.width;
					}
					if(s.cap != "butt"){
						for(var i = 0; i < da.length; i += 2){
							da[i] -= s.width;
							if(da[i] < 1){ da[i] = 1; }
						}
						for(var i = 1; i < da.length; i += 2){
							da[i] += s.width;
						}
					}
					da = da.join(",");
				}
				rn.setAttribute("stroke-dasharray", da);
				rn.setAttribute("dojoGfxStrokeStyle", s.style);
			}
			return this;	// self
		},

		_getParentSurface: function(){
			var surface = this.parent;
			for(; surface && !(surface instanceof g.Surface); surface = surface.parent);
			return surface;
		},

		_setFillObject: function(f, nodeType){
			var svgns = svg.xmlns.svg;
			this.fillStyle = f;
			var surface = this._getParentSurface(),
				defs = surface.defNode,
				fill = this.rawNode.getAttribute("fill"),
				ref  = svg.getRef(fill);
			if(ref){
				fill = ref;
				if(fill.tagName.toLowerCase() != nodeType.toLowerCase()){
					var id = fill.id;
					fill.parentNode.removeChild(fill);
					fill = _createElementNS(svgns, nodeType);
					fill.setAttribute("id", id);
					defs.appendChild(fill);
				}else{
					while(fill.childNodes.length){
						fill.removeChild(fill.lastChild);
					}
				}
			}else{
				fill = _createElementNS(svgns, nodeType);
				fill.setAttribute("id", g._base._getUniqueId());
				defs.appendChild(fill);
			}
			if(nodeType == "pattern"){
				fill.setAttribute("patternUnits", "userSpaceOnUse");
				var img = _createElementNS(svgns, "image");
				img.setAttribute("x", 0);
				img.setAttribute("y", 0);
				img.setAttribute("width",  f.width .toFixed(8));
				img.setAttribute("height", f.height.toFixed(8));
				img.setAttributeNS(svg.xmlns.xlink, "xlink:href", f.src);
				fill.appendChild(img);
			}else{
				fill.setAttribute("gradientUnits", "userSpaceOnUse");
				for(var i = 0; i < f.colors.length; ++i){
					var c = f.colors[i], t = _createElementNS(svgns, "stop"),
						cc = c.color = g.normalizeColor(c.color);
					t.setAttribute("offset",       c.offset.toFixed(8));
					t.setAttribute("stop-color",   cc.toCss());
					t.setAttribute("stop-opacity", cc.a);
					fill.appendChild(t);
				}
			}
			this.rawNode.setAttribute("fill", "url(#" + fill.getAttribute("id") +")");
			this.rawNode.removeAttribute("fill-opacity");
			this.rawNode.setAttribute("fill-rule", "evenodd");
			return fill;
		},

		_applyTransform: function() {
			var matrix = this.matrix;
			if(matrix){
				var tm = this.matrix;
				this.rawNode.setAttribute("transform", "matrix(" +
					tm.xx.toFixed(8) + "," + tm.yx.toFixed(8) + "," +
					tm.xy.toFixed(8) + "," + tm.yy.toFixed(8) + "," +
					tm.dx.toFixed(8) + "," + tm.dy.toFixed(8) + ")");
			}else{
				this.rawNode.removeAttribute("transform");
			}
			return this;
		},

		setRawNode: function(rawNode){
			// summary:
			//	assigns and clears the underlying node that will represent this
			//	shape. Once set, transforms, gradients, etc, can be applied.
			//	(no fill & stroke by default)
			var r = this.rawNode = rawNode;
			if(this.shape.type!="image"){
				r.setAttribute("fill", "none");
			}
			r.setAttribute("fill-opacity", 0);
			r.setAttribute("stroke", "none");
			r.setAttribute("stroke-opacity", 0);
			r.setAttribute("stroke-width", 1);
			r.setAttribute("stroke-linecap", "butt");
			r.setAttribute("stroke-linejoin", "miter");
			r.setAttribute("stroke-miterlimit", 4);
		},

		setShape: function(newShape){
			// summary: sets a shape object (SVG)
			// newShape: Object: a shape object
			//	(see dojox.gfx.defaultPath,
			//	dojox.gfx.defaultPolyline,
			//	dojox.gfx.defaultRect,
			//	dojox.gfx.defaultEllipse,
			//	dojox.gfx.defaultCircle,
			//	dojox.gfx.defaultLine,
			//	or dojox.gfx.defaultImage)
			this.shape = g.makeParameters(this.shape, newShape);
			for(var i in this.shape){
				if(i != "type"){
					this.rawNode.setAttribute(i, this.shape[i]);
				}
			}
			this.bbox = null;
			return this;	// self
		},

		// move family

		_moveToFront: function(){
			// summary: moves a shape to front of its parent's list of shapes (SVG)
			this.rawNode.parentNode.appendChild(this.rawNode);
			return this;	// self
		},
		_moveToBack: function(){
			// summary: moves a shape to back of its parent's list of shapes (SVG)
			this.rawNode.parentNode.insertBefore(this.rawNode, this.rawNode.parentNode.firstChild);
			return this;	// self
		}
	});

	dojo.declare("dojox.gfx.svg.Group", svg.Shape, {
		// summary: a group shape (SVG), which can be used
		//	to logically group shapes (e.g, to propagate matricies)
		constructor: function(){
			gs.Container._init.call(this);
		},
		setRawNode: function(rawNode){
			// summary: sets a raw SVG node to be used by this shape
			// rawNode: Node: an SVG node
			this.rawNode = rawNode;
		}
	});
	svg.Group.nodeType = "g";

	dojo.declare("dojox.gfx.svg.Rect", [svg.Shape, gs.Rect], {
		// summary: a rectangle shape (SVG)
		setShape: function(newShape){
			// summary: sets a rectangle shape object (SVG)
			// newShape: Object: a rectangle shape object
			this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			for(var i in this.shape){
				if(i != "type" && i != "r"){
					this.rawNode.setAttribute(i, this.shape[i]);
				}
			}
			if(this.shape.r){
				this.rawNode.setAttribute("ry", this.shape.r);
				this.rawNode.setAttribute("rx", this.shape.r);
			}
			return this;	// self
		}
	});
	svg.Rect.nodeType = "rect";

	dojo.declare("dojox.gfx.svg.Ellipse", [svg.Shape, gs.Ellipse], {});
	svg.Ellipse.nodeType = "ellipse";

	dojo.declare("dojox.gfx.svg.Circle", [svg.Shape, gs.Circle], {});
	svg.Circle.nodeType = "circle";

	dojo.declare("dojox.gfx.svg.Line", [svg.Shape, gs.Line], {});
	svg.Line.nodeType = "line";

	dojo.declare("dojox.gfx.svg.Polyline", [svg.Shape, gs.Polyline], {
		// summary: a polyline/polygon shape (SVG)
		setShape: function(points, closed){
			// summary: sets a polyline/polygon shape object (SVG)
			// points: Object: a polyline/polygon shape object
			if(points && points instanceof Array){
				// branch
				// points: Array: an array of points
				this.shape = g.makeParameters(this.shape, { points: points });
				if(closed && this.shape.points.length){
					this.shape.points.push(this.shape.points[0]);
				}
			}else{
				this.shape = g.makeParameters(this.shape, points);
			}
			this.bbox = null;
			this._normalizePoints();
			var attr = [], p = this.shape.points;
			for(var i = 0; i < p.length; ++i){
				attr.push(p[i].x.toFixed(8), p[i].y.toFixed(8));
			}
			this.rawNode.setAttribute("points", attr.join(" "));
			return this;	// self
		}
	});
	svg.Polyline.nodeType = "polyline";

	dojo.declare("dojox.gfx.svg.Image", [svg.Shape, gs.Image], {
		// summary: an image (SVG)
		setShape: function(newShape){
			// summary: sets an image shape object (SVG)
			// newShape: Object: an image shape object
			this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var rawNode = this.rawNode;
			for(var i in this.shape){
				if(i != "type" && i != "src"){
					rawNode.setAttribute(i, this.shape[i]);
				}
			}
			rawNode.setAttribute("preserveAspectRatio", "none");
			rawNode.setAttributeNS(svg.xmlns.xlink, "xlink:href", this.shape.src);
			return this;	// self
		}
	});
	svg.Image.nodeType = "image";

	dojo.declare("dojox.gfx.svg.Text", [svg.Shape, gs.Text], {
		// summary: an anchored text (SVG)
		setShape: function(newShape){
			// summary: sets a text shape object (SVG)
			// newShape: Object: a text shape object
			this.shape = g.makeParameters(this.shape, newShape);
			this.bbox = null;
			var r = this.rawNode, s = this.shape;
			r.setAttribute("x", s.x);
			r.setAttribute("y", s.y);
			r.setAttribute("text-anchor", s.align);
			r.setAttribute("text-decoration", s.decoration);
			r.setAttribute("rotate", s.rotated ? 90 : 0);
			r.setAttribute("kerning", s.kerning ? "auto" : 0);
			r.setAttribute("text-rendering", "optimizeLegibility");
			
			// update the text content
			if(r.firstChild){
				r.firstChild.nodeValue = s.text;
			}else{
				r.appendChild(_createTextNode(s.text));
			}
			return this;	// self
		},
		getTextWidth: function(){
			// summary: get the text width in pixels
			var rawNode = this.rawNode,
				oldParent = rawNode.parentNode,
				_measurementNode = rawNode.cloneNode(true);
			_measurementNode.style.visibility = "hidden";

			// solution to the "orphan issue" in FF
			var _width = 0, _text = _measurementNode.firstChild.nodeValue;
			oldParent.appendChild(_measurementNode);

			// solution to the "orphan issue" in Opera
			// (nodeValue == "" hangs firefox)
			if(_text!=""){
				while(!_width){
//Yang: work around svgweb bug 417 -- http://code.google.com/p/svgweb/issues/detail?id=417
if (_measurementNode.getBBox)
					_width = parseInt(_measurementNode.getBBox().width);
else
	_width = 68;
				}
			}
			oldParent.removeChild(_measurementNode);
			return _width;
		}
	});
	svg.Text.nodeType = "text";

	dojo.declare("dojox.gfx.svg.Path", [svg.Shape, g.path.Path], {
		// summary: a path shape (SVG)
		_updateWithSegment: function(segment){
			// summary: updates the bounding box of path with new segment
			// segment: Object: a segment
			this.inherited(arguments);
			if(typeof(this.shape.path) == "string"){
				this.rawNode.setAttribute("d", this.shape.path);
			}
		},
		setShape: function(newShape){
			// summary: forms a path using a shape (SVG)
			// newShape: Object: an SVG path string or a path object (see dojox.gfx.defaultPath)
			this.inherited(arguments);
			if(this.shape.path){
				this.rawNode.setAttribute("d", this.shape.path);
			}else{
				this.rawNode.removeAttribute("d");
			}
			return this;	// self
		}
	});
	svg.Path.nodeType = "path";

	dojo.declare("dojox.gfx.svg.TextPath", [svg.Shape, g.path.TextPath], {
		// summary: a textpath shape (SVG)
		_updateWithSegment: function(segment){
			// summary: updates the bounding box of path with new segment
			// segment: Object: a segment
			this.inherited(arguments);
			this._setTextPath();
		},
		setShape: function(newShape){
			// summary: forms a path using a shape (SVG)
			// newShape: Object: an SVG path string or a path object (see dojox.gfx.defaultPath)
			this.inherited(arguments);
			this._setTextPath();
			return this;	// self
		},
		_setTextPath: function(){
			if(typeof this.shape.path != "string"){ return; }
			var r = this.rawNode;
			if(!r.firstChild){
				var tp = _createElementNS(svg.xmlns.svg, "textPath"),
					tx = _createTextNode("");
				tp.appendChild(tx);
				r.appendChild(tp);
			}
			var ref  = r.firstChild.getAttributeNS(svg.xmlns.xlink, "href"),
				path = ref && svg.getRef(ref);
			if(!path){
				var surface = this._getParentSurface();
				if(surface){
					var defs = surface.defNode;
					path = _createElementNS(svg.xmlns.svg, "path");
					var id = g._base._getUniqueId();
					path.setAttribute("id", id);
					defs.appendChild(path);
					r.firstChild.setAttributeNS(svg.xmlns.xlink, "xlink:href", "#" + id);
				}
			}
			if(path){
				path.setAttribute("d", this.shape.path);
			}
		},
		_setText: function(){
			var r = this.rawNode;
			if(!r.firstChild){
				var tp = _createElementNS(svg.xmlns.svg, "textPath"),
					tx = _createTextNode("");
				tp.appendChild(tx);
				r.appendChild(tp);
			}
			r = r.firstChild;
			var t = this.text;
			r.setAttribute("alignment-baseline", "middle");
			switch(t.align){
				case "middle":
					r.setAttribute("text-anchor", "middle");
					r.setAttribute("startOffset", "50%");
					break;
				case "end":
					r.setAttribute("text-anchor", "end");
					r.setAttribute("startOffset", "100%");
					break;
				default:
					r.setAttribute("text-anchor", "start");
					r.setAttribute("startOffset", "0%");
					break;
			}
			//r.parentNode.setAttribute("alignment-baseline", "central");
			//r.setAttribute("dominant-baseline", "central");
			r.setAttribute("baseline-shift", "0.5ex");
			r.setAttribute("text-decoration", t.decoration);
			r.setAttribute("rotate", t.rotated ? 90 : 0);
			r.setAttribute("kerning", t.kerning ? "auto" : 0);
			r.firstChild.data = t.text;
		}
	});
	svg.TextPath.nodeType = "text";

	dojo.declare("dojox.gfx.svg.Surface", gs.Surface, {
		// summary: a surface object to be used for drawings (SVG)
		constructor: function(){
			gs.Container._init.call(this);
		},
		destroy: function(){
			this.defNode = null;	// release the external reference
			this.inherited(arguments);
		},
		setDimensions: function(width, height){
			// summary: sets the width and height of the rawNode
			// width: String: width of surface, e.g., "100px"
			// height: String: height of surface, e.g., "100px"
			if(!this.rawNode){ return this; }
			this.rawNode.setAttribute("width",  width);
			this.rawNode.setAttribute("height", height);
			return this;	// self
		},
		getDimensions: function(){
			// summary: returns an object with properties "width" and "height"
			var t = this.rawNode ? {
				width:  g.normalizedLength(this.rawNode.getAttribute("width")),
				height: g.normalizedLength(this.rawNode.getAttribute("height"))} : null;
			return t;	// Object
		}
	});

	svg.createSurface = function(parentNode, width, height){
		// summary: creates a surface (SVG)
		// parentNode: Node: a parent node
		// width: String: width of surface, e.g., "100px"
		// height: String: height of surface, e.g., "100px"

		var s = new svg.Surface();
		s.rawNode = _createElementNS(svg.xmlns.svg, "svg");
		if(width){
			s.rawNode.setAttribute("width",  width);
		}
		if(height){
			s.rawNode.setAttribute("height", height);
		}

		var defNode = _createElementNS(svg.xmlns.svg, "defs");
		s.rawNode.appendChild(defNode);
		s.defNode = defNode;

		s._parent = d.byId(parentNode);
		s._parent.appendChild(s.rawNode);

		return s;	// dojox.gfx.Surface
	};

	// Extenders

	var Font = {
		_setFont: function(){
			// summary: sets a font object (SVG)
			var f = this.fontStyle;
			// next line doesn't work in Firefox 2 or Opera 9
			//this.rawNode.setAttribute("font", dojox.gfx.makeFontString(this.fontStyle));
			this.rawNode.setAttribute("font-style", f.style);
			this.rawNode.setAttribute("font-variant", f.variant);
			this.rawNode.setAttribute("font-weight", f.weight);
			this.rawNode.setAttribute("font-size", f.size);
			this.rawNode.setAttribute("font-family", f.family);
		}
	};

	var C = gs.Container, Container = {
		openBatch: function() {
			// summary: starts a new batch, subsequent new child shapes will be held in
			//	the batch instead of appending to the container directly
			this.fragment = _createFragment();
		},
		closeBatch: function() {
			// summary: submits the current batch, append all pending child shapes to DOM
			if (this.fragment) {
				this.rawNode.appendChild(this.fragment);
				delete this.fragment;
			}
		},
		add: function(shape){
			// summary: adds a shape to a group/surface
			// shape: dojox.gfx.Shape: an VML shape object
			if(this != shape.getParent()){
				if (this.fragment) {
					this.fragment.appendChild(shape.rawNode);
				} else {
					this.rawNode.appendChild(shape.rawNode);
				}
				C.add.apply(this, arguments);
			}
			return this;	// self
		},
		remove: function(shape, silently){
			// summary: remove a shape from a group/surface
			// shape: dojox.gfx.Shape: an VML shape object
			// silently: Boolean?: if true, regenerate a picture
			if(this == shape.getParent()){
				if(this.rawNode == shape.rawNode.parentNode){
					this.rawNode.removeChild(shape.rawNode);
				}
				if(this.fragment && this.fragment == shape.rawNode.parentNode){
					this.fragment.removeChild(shape.rawNode);
				}
				C.remove.apply(this, arguments);
			}
			return this;	// self
		},
		clear: function(){
			// summary: removes all shapes from a group/surface
			var r = this.rawNode;
			while(r.lastChild){
				r.removeChild(r.lastChild);
			}
			var defNode = this.defNode;
			if(defNode){
				while(defNode.lastChild){
					defNode.removeChild(defNode.lastChild);
				}
				r.appendChild(defNode);
			}
			return C.clear.apply(this, arguments);
		},
		_moveChildToFront: C._moveChildToFront,
		_moveChildToBack:  C._moveChildToBack
	};

	var Creator = {
		// summary: SVG shape creators
		createObject: function(shapeType, rawShape){
			// summary: creates an instance of the passed shapeType class
			// shapeType: Function: a class constructor to create an instance of
			// rawShape: Object: properties to be passed in to the classes "setShape" method
			if(!this.rawNode){ return null; }
			var shape = new shapeType(),
				node = _createElementNS(svg.xmlns.svg, shapeType.nodeType);

			shape.setRawNode(node);
			shape.setShape(rawShape);
			// rawNode.appendChild() will be done inside this.add(shape) below
			this.add(shape);
			return shape;	// dojox.gfx.Shape
		}
	};

	d.extend(svg.Text, Font);
	d.extend(svg.TextPath, Font);

	d.extend(svg.Group, Container);
	d.extend(svg.Group, gs.Creator);
	d.extend(svg.Group, Creator);

	d.extend(svg.Surface, Container);
	d.extend(svg.Surface, gs.Creator);
	d.extend(svg.Surface, Creator);


	// some specific override for svgweb + flash
	if(svg.useSvgWeb){
		// override createSurface()
		svg.createSurface = function(parentNode, width, height){
			var s = new svg.Surface();

			// ensure width / height
			if(!width || !height){
				var pos = d.position(parentNode);
				width  = width  || pos.w;
				height = height || pos.h;
			}

			// ensure id
			parentNode = d.byId(parentNode);
			var id = parentNode.id ? parentNode.id+'_svgweb' : g._base._getUniqueId();
			
			// create dynamic svg root
			var mockSvg = _createElementNS(svg.xmlns.svg, 'svg');
			mockSvg.id = id;
			mockSvg.setAttribute('width', width);
			mockSvg.setAttribute('height', height);
			svgweb.appendChild(mockSvg, parentNode);

			// notice: any call to the raw node before flash init will fail.
			mockSvg.addEventListener('SVGLoad', function(){
				// become loaded
				s.rawNode = this;
				s.isLoaded = true;
				
				// init defs
				var defNode = _createElementNS(svg.xmlns.svg, "defs");
				s.rawNode.appendChild(defNode);
				s.defNode = defNode;
				
				// notify application
				if (s.onLoad)
					s.onLoad(s);
			}, false);

			// flash not loaded yet
			s.isLoaded = false;
			return s;
		};
		
		// override Surface.destroy()
		svg.Surface.extend({
			destroy: function(){
				var mockSvg = this.rawNode;
				svgweb.removeChild(mockSvg, mockSvg.parentNode);
			}
		});

		// override connect() & disconnect() for Shape & Surface event processing
		var _eventsProcessing = {
			connect: function(name, object, method){
				// connect events using the mock addEventListener() provided by svgweb
				if (name.substring(0, 2)==='on') { name = name.substring(2); }
				if (arguments.length == 2) {
					method = object;
				} else {
					method = d.hitch(object, method);
				}
				this.getEventSource().addEventListener(name, method, false);
				return [this, name, method];
			},
			disconnect: function(token){
				// disconnect events using the mock removeEventListener() provided by svgweb
				this.getEventSource().removeEventListener(token[1], token[2], false);
				delete token[0];
			}
		};
		
		dojo.extend(svg.Shape, _eventsProcessing);
		dojo.extend(svg.Surface, _eventsProcessing);
	}

	// see if we are required to initilize
	if(g.loadAndSwitch === "svg"){
		g.switchTo("svg");
		delete g.loadAndSwitch;
	}
})();
