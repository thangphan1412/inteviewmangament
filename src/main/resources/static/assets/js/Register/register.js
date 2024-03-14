$(document).ready(function () {
    $('#buttonSubmit').click(function (e) {
        e.preventDefault();
        var user = {
            "userName": $("#username").val(),
            "password": $("#password").val(),
            "fullName": $("#fullName").val(),
            "email": $("#email").val(),
            "dob": $("#dob").val(),
            "address": $("#address").val(),
            "phoneNo": $("#phoneNo").val(),
            "gender": $("#gender").val(),
            "role": $("#role").val(),
            "department": $("#department").val(),
            "status": $("#status").val(),
            "note": $("#note").val()
        };

        Swal.fire({
            title: 'Process...',
            html: 'Please wait...',
            allowEscapeKey: false,
            allowOutsideClick: false,
            didOpen: () => {
                Swal.showLoading()
            }
        });
        $.ajax({
            type: 'POST',
            url: '/register',
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function (data) {
                // Xử lý kết quả trả về khi server xử lý thành công
                Swal.close();
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
                    title: 'Create candidate successfully'
                })
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Xử lý lỗi khi server xử lý thất bại
                Swal.close();

                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 3000,
                    timerProgressBar: true,
                });

                Toast.fire({
                    icon: 'error',
                    title: 'Create candidate failed'
                })

            }
        });
    });
});
