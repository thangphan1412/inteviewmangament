$('input[type="submit"]').on('click', (e) => {
    e.preventDefault();
    Swal.fire({
        title: 'Process...',
        html: 'Please wait...',
        allowEscapeKey: false,
        allowOutsideClick: false,
        didOpen: () => {
            Swal.showLoading()
        }
    });
    var resetPassword = {
        "newPassword": $("#password").val(),
        "token": $("#token").val()
    };
    $.ajax({
        type: 'POST',
        url: '/reset_password',
        contentType: "application/json",
        data: JSON.stringify(resetPassword),
        success: function (data) {
            Swal.close();
            const Toast = Swal.mixin({
                toast: true,
                position: 'top-end',
                showConfirmButton: false,
                timer: 3000,
                timerProgressBar: true,
            });
            Toast.fire({
                icon: 'success',
                title: 'Reset password successfully'
            })
        },
        error: function (jqXHR, textStatus, errorThrown) {
            Swal.close();
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
                title: 'Reset password failed'
            })
        }
    });
})