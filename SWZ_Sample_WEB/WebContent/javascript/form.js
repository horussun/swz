
function checkCal(form, pre, mask, val) {
    if (val == "") {
        if ((mask & 1) != 0) {
            form[pre + ":Year"].options.selectedIndex = 0;
        }
        if ((mask & 2) != 0) {
            form[pre + ":Year"].options.selectedIndex = 0;
        }
        if ((mask & 4) != 0) {
            form[pre + ":Month"].options.selectedIndex = 0;
        }
        if ((mask & 8) != 0) {
            form[pre + ":Day"].options.selectedIndex = 0;
        }
        if ((mask & 16) != 0) {
            form[pre + ":Hour"].options.selectedIndex = 0;
            if (form[pre + ":AmPm"]) {
                form[pre + ":AmPm"].options.selectedIndex = 0;
            }
        }
        if ((mask & 32) != 0) {
            form[pre + ":Min"].options.selectedIndex = 0;
        }
        if ((mask & 64) != 0) {
            form[pre + ":Sec"].options.selectedIndex = 0;
        }
        if ((mask & 128) != 0) {
            form[pre + ":TimeZone"].options.selectedIndex = 0;
        }
        return false;
    }
    return true;
}
function adjustDays(mF, yF, dF, n) {
    var m = mF.options.selectedIndex - n;
    var y = yF.options[yF.options.selectedIndex].value;
    var d = dF.options.selectedIndex;
    if (m == 3 || m == 5 || m == 8 || m == 10) {
        days = 30;
    } else {
        if (m == 1) {
            if ((y % 4) == 0) {
                days = 29;
            } else {
                days = 28;
            }
        } else {
            days = 31;
        }
    }
    if (dF.length != (days + n)) {
        dF.options.length = 28 + n;
        for (var i = 28; i < days; i++) {
            dF.options.length++;
            dF.options[i + n].text = (i + 1);
            dF.options[i + n].value = (i + 1);
            if ((dF.length % 2) == 0) {
                dF.options[i + n].className = "se";
            } else {
                dF.options[i + n].className = "so";
            }
        }
    }
    if (d > (-1 + n) && d < (days + n)) {
        dF.options.selectedIndex = d;
    }
}
function syncCal(form, prop, syncProp, field) {
    if (field) {
        var fld = ":" + field;
        var p = form[prop + fld];
        var s = form[syncProp + fld];
        s.options.selectedIndex = getSyncIndex(p, s);
        var possNull = (s.options[0].value == "") ? 1 : 0;
        if (field == "Month" || field == "Year") {
            adjustDays(form[syncProp + ":Month"], form[syncProp + ":Year"], form[syncProp + ":Day"], possNull);
        }
    } else {
        var flds = [":Year", ":Month", ":Day", ":Hour", ":Min", ":AmPm"];
        for (var i = 0; i < flds.length; i++) {
            if (form[prop + flds[i]]) {
                var p = form[prop + flds[i]];
                var s = form[syncProp + flds[i]];
                s.options.selectedIndex = getSyncIndex(p, s);
            }
        }
        var possNull = (form[syncProp + flds[0]].options[0].value == "") ? 1 : 0;
        adjustDays(form[syncProp + ":Month"], form[syncProp + ":Year"], form[syncProp + ":Day"], possNull);
    }
}
function getSyncIndex(p, s) {
    var propval = p.options[0].value;
    var syncval = s.options[0].value;
    var adjust = 0;
    if (propval != syncval) {
        if (propval == "") {
            adjust = -1;
        } else {
            adjust = 1;
        }
    }
    return p.options.selectedIndex + adjust;
}
function getValue(fm, name) {
    var fld = fm[name];
    if (fld == null) {
        return null;
    }
    return getFieldValue(fld);
}
function getFieldValue(fld) {
    if (fld["options"] != null) {
        var ind = fld.selectedIndex;
        if (ind != -1) {
            return fld.options[ind].value;
        } else {
            return null;
        }
    } else {
        if (fld["length"] != null) {
            for (var i = 0; i < fld.length; i++) {
                if (fld[i].checked) {
                    return fld[i].value;
                }
            }
            return null;
        } else {
            return fld.value;
        }
    }
}
