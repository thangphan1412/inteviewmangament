$('.delete-btn').on('click', function () {
    var id = $(this).data('id');

    const swalWithBootstrapButtons = Swal.mixin({
        customClass: {
            confirmButton: 'btn btn-success',
            cancelButton: 'btn btn-danger me-2'
        },
        buttonsStyling: false,
    })

    swalWithBootstrapButtons.fire({
        title: 'Are you sure?',
        text: "You won't be able to revert this!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonClass: 'me-2',
        confirmButtonText: 'Yes, delete it!',
        cancelButtonText: 'No, cancel!',
    }).then((result) => {
        if (result.isConfirmed) {
            // Gọi AJAX để xóa với id lấy được ở trên
            $.ajax({
                url: '/Candidate/' + id,
                type: 'DELETE',
                success: function (result) {
                    // Xử lý khi xóa thành công
                    Swal.fire(
                        'Deleted!',
                        'Your data has been deleted.',
                        'success'
                    ).then(() => {
                        location.reload();
                    })
                },
                error: function (xhr, status, error) {
                    // Xử lý khi xóa thất bại
                    Swal.fire(
                        'Error!',
                        'Failed to delete data.',
                        'error'
                    )
                }
            })
        } else if (
            // Read more about handling dismissals
            result.dismiss === Swal.DismissReason.cancel
        ) {
            swalWithBootstrapButtons.fire(
                'Cancelled',
                'Your imaginary file is safe :)',
                'error'
            )
        }
    })
})
