$('button[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var user = {
        "userId": $("#userId").val(),
        "fullName": $("#fullName").val(),
        "email": $("#email").val(),
        "dob": $("#dob").val(),
        "address": $("#address").val(),
        "phoneNo": $("#phoneNo").val(),
        "gender": $("#gender").val(),
        "role": $("#role").val(),
        "department": $("#department").val(),
        "active": $("#status").val(),
        "note": $("#note").val()
    };
    $.ajax({
        type: 'PUT',
        url: '/User/updateUser',
        contentType: "application/json",
        data: JSON.stringify(user),
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            console.log("hello success");
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
            });

            Toast.fire({
                icon: 'success',
                title: 'Update candidate successfully'
            }).then(() => {
                location.reload();
            })
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Xử lý lỗi khi server xử lý thất bại
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
            });

            Toast.fire({
                icon: 'error',
                title: 'Update candidate failed'
            })

        }
    });
})