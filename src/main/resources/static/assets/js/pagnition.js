function paginate(currentPage, rowsPerPage) {
    var table = document.getElementById("myTable");
    var rows = table.getElementsByTagName("tr");
    var startIndex = (currentPage - 1) * rowsPerPage + 1;
    var endIndex = startIndex + rowsPerPage - 1;
    for (var i = 1; i < rows.length; i++) {
        if (i >= startIndex && i <= endIndex) {
            rows[i].style.display = "";
        } else {
            rows[i].style.display = "none";
        }
    }
    var pagination = document.getElementById("pagination");
    var pages = Math.ceil((rows.length - 1) / rowsPerPage);
    var buttons = "";
    for (var i = 1; i <= pages; i++) {
        if (i == currentPage) {
            buttons += '<li class="page-item active" ><a class="page-link">' + i + '</a></li>';
        } else {
            buttons += '<li class="page-item" onclick="paginate(' + i + ', ' + rowsPerPage + ')"><a class="page-link">' + i + '</a></li>';
        }
    }
    pagination.innerHTML = buttons;
}

function pageValue() {
    var rowNumber = document.getElementById('number-element-per-page').value;
    if (rowNumber == -1) {
        var table = document.getElementById("myTable");
        var rows = table.getElementsByTagName("tr");
        paginate(1, rows.length);
        return;
    }
    paginate(1, rowNumber);
}