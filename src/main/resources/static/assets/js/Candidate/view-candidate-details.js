function ViewCandidateDetails(id) {
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
        url: '/Candidate/viewDetails/' + id,
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            console.log(data);
            $("#candidateId").val(data.candidate.candidateId);
            $("#fullname").val(data.candidate.fullname);
            $("#email").val(data.candidate.email);
            $("#dob").val(data.candidate.dob)
            $("#address").val(data.candidate.address)
            $("#phoneNo").val(data.candidate.phoneNo)
            data.candidate.gender === true ? $("#gender").val('true') : $("#gender").val('false');
            $("#cvAttachment").val(data.candidate.cvAttachment)
            $("#candidateStatus").val(data.candidate.candidateStatus)
            $("#postion").val(data.candidate.postion)
            $("#yearOfExperience").val(data.candidate.yearOfExperience)
            let skillsArray = data.candidate.skills.split(",")
            $("#skills").val(skillsArray).trigger("change")
            $("#highest_level").val(data.candidate.highest_level)
            $("#recruiter").val(data.user.userId)
            // $.ajax({
            //     type: 'GET',
            //     url: '/User/getAllRecruiter',
            //     contentType: "application/json",
            //     success: function (recruiterList) {
            //         // Xử lý kết quả trả về khi server xử lý thành công
            //         console.log(recruiterList);
            //         // let selectRecruiter = document.getElementById("recruiter");
            //         //
            //         // for (let i = 0; i < recruiterList.length; i++) {
            //         //     let recruiter = recruiterList[i];
            //         //
            //         //     // Tạo một option mới
            //         //     let option = document.createElement("option");
            //         //     option.value = recruiter.userId;
            //         //     option.text = recruiter.userName;
            //         //
            //         //     if(data.user.userId === recruiter.userId) {
            //         //         option.selected = true;
            //         //     }
            //         //
            //         //     // Chèn option vào select
            //         //     selectRecruiter.appendChild(option);
            //         // }
            //         const recruiterData = recruiterList.map(recruit => (
            //             {
            //                 "id": recruit.userId,
            //                 "text": recruit.userName
            //             }
            //         ))
            //         console.log(recruiterData);
            //         $("#recruiter").select2({
            //             data: recruiterData,
            //             dropdownParent: $("#myModal")
            //         })
            //     },
            //     error: function (jqXHR, textStatus, errorThrown) {
            //
            //     }
            // });
            $("#note").val(data.note)
            checkFileUploadIsEmpty();
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });
}

function checkFileUploadIsEmpty() {
    if ($('#cvAttachment').val().length == 0) {
        $('#pdf-file').css('display', 'block');
        $('#cvAttachment').css('display', 'none')
    } else {
        $('#cvAttachment').css('display', 'block');
        $('#pdf-file').css('display', 'none')
    }
}
