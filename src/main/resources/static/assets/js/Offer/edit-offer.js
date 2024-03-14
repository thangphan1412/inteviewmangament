$('button[type="submit"]').on('click', (e) => {
    e.preventDefault();
    var offer = {
        "candidate": {
            "candidateId": $("#candidateSelect").val()
        },
        "user": {
            "userId": $("#recruiterOwner").val()
        },
        "approveBy": {
            "userId": $("#approveBy").val()
        },
        "offer": {
            "offerId": $("#offerId").val(),
            "contractType": $("#contractType").val(),
            "positionId": $("#positionId").val(),
            "level": $("#level").val(),
            "department": $("#department").val(),
            "dueDate": $("#dueDate").val(),
            "contractStart": $("#contractStart").val(),
            "contractEnd": $("#contractEnd").val(),
            "basicSalary": $("#basicSalary").val(),
            "note": $("#notes").val()
        },
        "offerStatus": $("#offerStatus").val()
    };

    $.validator.addMethod("validateContractTo", function (value, element) {
        return this.optional(element) || enddate >= stadate;
    }, "Contract to > Contract from");

    $.validator.addMethod("validateContractFrom", function (value, element) {
        return this.optional(element) || stadate >= curdate;
    }, "ContractFrom lớn hơn hiện taij");

    $.validator.addMethod("validateDueDate", function (value, element) {
        return this.optional(element) || dueDate >= curdate;
    }, "DueDate lớn hơn hiện taij");


    let today = new Date();
    let date = "'" + today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate() + "'";
    const curdate = new Date(date);
    const stadate = new Date($("#contractStart").val())
    const enddate = new Date($("#contractEnd").val())
    const dueDate = new Date($("#dueDate").val())


    $("#formValidate").validate({
        rules: {
            candidateSelect: {
                required: true,
            },
            contractType: {
                required: true,
            },
            positionId: {
                required: true,
            },
            level: {
                required: true,
            },
            approveBy: {
                required: true,
            },
            department: {
                required: true,
            },
            candidateStatus: {
                required: true,
            },
            recruiterOwner: {
                required: true,
            },
            interviewer: {
                required: true,
            },
            dueDate: {
                required: true,
                validateDueDate: true
            },
            contractStart: {
                required: true,
                validateContractFrom: true
            },
            contractEnd: {
                required: true,
                validateContractTo: true
            },
            basicSalary: {
                required: true
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
            type: 'PUT',
            url: '/offer/updateOffer',
            contentType: "application/json",
            data: JSON.stringify(offer),
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
                    title: 'Update offer successfully'
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
                    title: 'Update offer failed'
                })

            }
        });
    }
})

function assignMe() {
    let username = $('#username').text();
    $("recruiterOwner")
}