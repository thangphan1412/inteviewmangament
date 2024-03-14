$('button[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var editJob = {
        "jobId": $("#jobId").val(),
        "title": $("#title").val(),
        "status": $("#status").val(),
        "skill": $("#skill").val().join(","),
        "startDate": $("#startDate").val(),
        "endDate": $("#endDate").val(),
        "benefit": $("#benefit").val().join(","),
        "salaryRangeFrom": $("#salaryRangeFrom").val(),
        "salaryRangeTo": $("#salaryRangeTo").val(),
        "address": $("#address").val(),
        "description": $("#description").val(),
    };

    $.validator.addMethod("validateEndDate", function (value, element) {
        return this.optional(element) || stadate >= $("#startDate").val();
    }, "EndDate lớn hơn StartDate");

    $.validator.addMethod("validateStarDate", function (value, element) {
        return this.optional(element) || stadate >= curdate;
    }, "StartDate lớn hơn hiện taij");

    $.validator.addMethod("validateSalary", function (value, element) {
        return this.optional(element) || value >= $("#salaryRangeFrom").val();
    }, "RangeTo > RangeFrom");

    let today = new Date();
    let date = "'" + today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate() + "'";
    const curdate = new Date(date);
    const stadate = new Date($("#startDate").val())
    console.log(date.valueOf())

    $("#job-detail").validate({
        rules: {
            title: {
                required: true,
            },
            skill: {
                required: true,
            },
            startDate: {
                required: true,
                validateStarDate: true
            },
            endDate: {
                required: true,
                validateEndDate: true
            },
            status: {
                required: true,
            },
            salaryRangeFrom: {
                required: true,
                digits: true
            },
            salaryRangeTo: {
                required: true,
                digits: true,
                validateSalary: true
            },
            benefit: {
                required: true,
            }

        },
        errorPlacement: function (label, element) {
            label.addClass('mt-1 tx-13 text-danger');
            label.insertAfter(element);
        },
        highlight: function (element, errorClass) {
            $(element).parent().addClass('validation-error')
            $(element).addClass('border-danger')
        }
    });


    if ($("#job-detail").valid()) {
        $.ajax({
            type: 'PUT',
            url: '/Job/editJob',
            contentType: "application/json",
            data: JSON.stringify(editJob),
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
                    title: 'Update job successfully'
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
                    title: 'Update job failed'
                })

            }
        });
    }


})