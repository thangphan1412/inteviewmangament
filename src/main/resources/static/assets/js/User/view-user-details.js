function ViewUserDetails(id) {
    $.ajax({
        type: 'GET',
        url: '/User/' + id,
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            console.log(data);
            $("#userId").val(data.userId),
                $("#fullName").val(data.fullName),
                $("#email").val(data.email),
                $("#dob").val(data.dob),
                $("#address").val(data.address),
                $("#phoneNo").val(data.phoneNo),
                $("#gender").val(data.gender ? 'true' : 'false'),
                $("#role").val(data.role),
                $("#department").val(data.department),
                $("#status").val(data.status ? 'true' : 'false'),
                $("#note").val(data.note)
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });

}