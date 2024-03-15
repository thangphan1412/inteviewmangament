$(document).ready(function () {
    $('#buttonSubmit').click(function (e) {
        e.preventDefault();
        var user = {
            "userName": $("#username").val(),
            "password": $("#password").val()
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
            url: '/login',
            contentType: "application/json",
            data: JSON.stringify(user),
            success: function (data) {
                // Xử lý kết quả trả về khi server xử lý thành công
                Swal.close();
                console.log(data);
                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                });

                Toast.fire({
                    icon: 'success',
                    title: 'Login successfully'
                })

                sessionStorage.setItem("user", JSON.stringify(data));
                window.location.href = "/home";
            },
            error: function (jqXHR, textStatus, errorThrown) {
                // Xử lý lỗi khi server xử lý thất bại
                Swal.close();

                const Toast = Swal.mixin({
                    toast: true,
                    position: 'top-end',
                    showConfirmButton: false,
                    timer: 2000,
                    timerProgressBar: true,
                });

                Toast.fire({
                    icon: 'error',
                    title: 'Login failed'
                })

            }
        });
    });
});
