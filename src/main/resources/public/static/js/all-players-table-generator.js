$(document).ready(function () {
    $.fn.dataTable.enum(["Quarterback", "Running Back", "Wide Receiver", "Tight End", "Offensive Line", "Defensive End", "Defensive Tackle", "Linebacker", "Cornerback", "Safety", "Kicker/Punter", "Unknown"]);
    var t = $("#table").DataTable({
        dom: "<'row'<'col-sm-6'B><'col-sm-6'f>><'row'<'col-sm-12'tr>><'row'<'col-sm-5'i><'col-sm-7'p>>",
        scrollX: !0,
        paging: !1,
        order: [[5, "desc"]],
        buttons: ["csv", {
            text: "Toggle All", action: function () {
                t.columns().visible(!t.column(0).visible())
            }
        }, {
            text: "Toggle Attributes", action: function () {
                t.columns([9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23]).visible(!t.column(9).visible())
            }
        }, {extend: 'colvis', text: 'Toggle Columns'}, {
            extend: 'collection',
            text: 'Position',
            buttons: [{
                text: "All", action: function () {
                    t.columns(4).search('').draw()
                }
            }, {
                text: "QB", action: function () {
                    t.columns(4).search("Quarterback").draw()
                }
            }, {
                text: "RB", action: function () {
                    t.columns(4).search("Running Back").draw()
                }
            }, {
                text: "WR", action: function () {
                    t.columns(4).search("Wide Receiver").draw()
                }
            }, {
                text: "TE", action: function () {
                    t.columns(4).search("Tight End").draw()
                }
            }, {
                text: "OL", action: function () {
                    t.columns(4).search("Offensive Line").draw()
                }
            }, {
                text: "DT", action: function () {
                    t.columns(4).search("Defensive Tackle").draw()
                }
            }, {
                text: "DE", action: function () {
                    t.columns(4).search("Defensive End").draw()
                }
            }, {
                text: "LB", action: function () {
                    t.columns(4).search("Linebacker").draw()
                }
            }, {
                text: "CB", action: function () {
                    t.columns(4).search("Cornerback").draw()
                }
            }, {
                text: "S", action: function () {
                    t.columns(4).search("Safety").draw()
                }
            }, {
                text: "K/P", action: function () {
                    t.columns(4).search("Kicker/Punter").draw()
                }
            }],
            autoClose: true
        }],
        data: dataSet,
        columns: [{title: "User"}, {title: "Draft Year"}, {title: "Team"}, {title: "Name"}, {title: "Position"}, {title: "Current TPE"}, {title: "Highest TPE"}, {title: "Last Updated"}, {title: "Last Seen"}, {title: "Str"}, {title: "Agi"}, {title: "Arm"}, {title: "Int"}, {title: "Thr"}, {title: "Tck"}, {title: "Spd"}, {title: "Hnd"}, {title: "PBlk"}, {title: "RBlk"}, {title: "End"}, {title: "KPow"}, {title: "KAcc"}, {title: "Height (inches)"}, {title: "Weight (lbs)"}]
    })
});
