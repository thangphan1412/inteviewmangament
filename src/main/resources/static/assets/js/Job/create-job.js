$(document).ready(function () {
    $('#buttonSubmit').click(function (e) {
        e.preventDefault();
        var job = {
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
            return this.optional(element) || enddate >= stadate;
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
        const enddate = new Date($("#endDate").val())
        console.log(date.valueOf())

        $("#formValidate").validate({
            rules: {
                title: {
                    required: true,
                    maxlength: 255
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
                },
                address: {
                    required: true,
                    maxlength: 255
                },
                description: {
                    required: true,
                    maxlength: 255
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

        if ($("#formValidate").valid()) {
            $.ajax({
                type: 'POST',
                url: '/Job/createJob',
                contentType: "application/json",
                data: JSON.stringify(job),
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
                        title: 'Create job successfully'
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
                        title: 'Create job failed'
                    })

                }
            });
        }
    });
});




