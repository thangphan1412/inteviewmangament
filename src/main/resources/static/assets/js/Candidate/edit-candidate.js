$('button[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var candidate = {
        "user": {
            "userId": $("#recruiter").val()
        },
        "candidate": {
            "candidateId": $("#candidateId").val(),
            "fullname": $("#fullname").val(),
            "email": $("#email").val(),
            "dob": $("#dob").val(),
            "address": $("#address").val(),
            "phoneNo": $("#phoneNo").val(),
            "gender": $("#gender").val(),
            "cvAttachment": $("#cvAttachment").val(),
            "candidateStatus": $("#candidateStatus").val(),
            "postion": $("#postion").val(),
            "yearOfExperience": $("#yearOfExperience").val(),
            "skills": $("#skills").val().join(","),
            "highest_level": $("#highest_level").val(),
            "note": $("#note").val()
        }
    };


    $.validator.addMethod("validateDoB", function (value, element) {
        return this.optional(element) || curYear - dob >= 18;
    }, "phải lớn hơn 18 tuổi");

    let today = new Date();
    let date = "'" + today.getFullYear() + "'";
    const curYear = new Date(date).getFullYear();
    const dob = new Date($("#dob").val()).getFullYear()

    $("#formValidate").validate({
        rules: {
            fullname: {
                required: true,
                maxlength: 255
            },
            email: {
                required: true,
                email: true,
                maxlength: 255
            },
            dob: {
                required: true,
                validateDoB: true
            },
            address: {
                required: true,
                maxlength: 255
            },
            phoneNo: {
                required: true,
                digits: true,
                minlength: 10,
                maxlength: 255
            },
            gender: {
                required: true,
            },
            cvAttachment: {
                required: true,
            },
            candidateStatus: {
                required: true,
                maxlength: 255
            },
            postion: {
                required: true
            },
            yearOfExperience: {
                required: true,
                digits: true
            },
            skills: {
                required: true
            },
            highest_level: {
                required: true
            },
            recruiter: {
                required: true
            },
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
            type: 'PUT',
            url: '/Candidate/updateCandidate',
            contentType: "application/json",
            data: JSON.stringify(candidate),
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
    }

})