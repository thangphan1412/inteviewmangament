function ViewOfferDetails(id) {
    // var candidate = {
    //     "fullname": $("#fullname").val(),
    //     "email": $("#email").val(),
    //     "dob": $("#dob").val(),
    //     "address": $("#address").val(),
    //     "phoneNo": $("#phoneNo").val(),
    //     "gender": $("#gender").val(),
    //     "cvAttachment": $("#cvAttachment").val(),
    //     "candidateStatus": $("#candidateStatus").val(),
    //     "postion": $("#postion").val(),
    //     "yearOfExperience": $("#yearOfExperience").val(),
    //     "skills": $("#skills").val().join(","),
    //     "highest_level": $("#highest_level").val(),
    //     "recruiter": $("#recruiter").val(),
    //     "note": $("#note").val()
    // };
    $.ajax({
        type: 'GET',
        url: '/offer/' + id,
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            console.log(data);
            $("#offerId").val(data.offerId);
            $("#candidateSelect").val(data.candidate.candidateId).trigger("change");
            $("#candidateSelect").select2('destroy');
            $("#contractType").val(data.contractType).trigger("change");
            $("#positionId").val(data.positionId).trigger("change");
            $("#level").val(data.level).trigger("change");
            $("#approveBy").val(data.approveBy.userId).trigger("change")
            $("#department").val(data.department).trigger("change")
            $("#candidateStatus").val(data.candidate.candidateStatus).trigger("change")
            $("#recruiterOwner").val(data.user.userId).trigger("change")
            $("#dueDate").val(data.dueDate)
            $("#contractStart").val(data.contractStart)
            $("#contractEnd").val(data.contractEnd)
            $("#basicSalary").val(data.basicSalary)
            $("#notes").val(data.note)
            if ($(".modal-footer").length) {
                if (data.candidate.candidateStatus === "Approved offer") {
                    $("#approveOfferButton").css("display", "none");
                } else if (data.candidate.candidateStatus === "Rejected offer") {
                    $("#rejectOfferButton").css("display", "none");
                }
            }
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });

}

function updateStatusOffer(choice) {
    var offer = {
        "candidate": {
            "candidateId": $("#candidateSelect").val()
        },
        "offerStatus": choice
    };
    $.ajax({
        type: 'PUT',
        url: '/offer/updateOfferStatus',
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

$('#candidateSelect').change(function (e) {
    $.ajax({
        type: 'GET',
        url: '/offer/autofillCandidate/' + $('#candidateSelect').val(),
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            $('#candidateStatus').val(data.candidateStatus);
            let arrayInterviewer = data.interviewerList;
            let interviewers = arrayInterviewer.map(interview => {
                return interview.userId
            })
            $('#interviewerNote').val(data.interviewNote);
            $('#interviewer').val(interviewers).trigger('change');
        },
        error: function (jqXHR, textStatus, errorThrown) {
            // Xử lý lỗi khi server xử lý thất bại
        }
    });
});