$('input[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var newPassword = {
        "newPassword": $("#newPassword").val()
    };
    $.ajax({
        type: 'POST',
        url: '/User/changePassword',
        contentType: "application/json",
        data: JSON.stringify(newPassword),
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
                title: 'Update password successfully'
            }).then(() => {
                Swal.fire({
                    title: 'Thông báo',
                    text: 'Bạn sẽ bị đăng xuất khỏi hệ thống',
                    icon: 'warning',
                    showCancelButton: false,
                    confirmButtonText: 'OK',
                    allowOutsideClick: false
                }).then((result) => {
                    if (result.isConfirmed) {
                        document.logoutForm.submit()
                    }
                });
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
                title: 'Update password failed'
            })

        }
    });
})