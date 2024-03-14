$('input[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var email = {
        "email": $("#email").val()
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
        url: '/forgot_password',
        contentType: "application/json",
        data: JSON.stringify(email),
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
                title: 'Send email successfully'
            });
        },
        error: function (jqXHR, textStatus, errorThrown) {
            Swal.close();
            if (jqXHR.responseText === 'Email not found') {
                Swal.fire({
                    icon: 'error',
                    title: 'Oops...',
                    text: jqXHR.responseText
                })
                return;
            } else {
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
                    title: 'Send email failed'
                })
            }

        }
    });
})