function ViewJobDetails(id) {
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
        url: '/Job/' + id,
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            console.log(data);
            $("#jobId").val(data.jobId);
            $("#title").val(data.title);
            $("#status").val(data.status);
            let skillsArray = data.skill.split(",")
            $("#skill").val(skillsArray).trigger("change");
            $("#startDate").val(data.startDate)
            $("#endDate").val(data.endDate)
            let benefitArray = data.benefit.split(",")
            $("#benefit").val(benefitArray).trigger("change");
            $("#salaryRangeFrom").val(data.salaryRangeFrom)
            $("#salaryRangeTo").val(data.salaryRangeTo)
            $("#address").val(data.address)
            $("#description").val(data.description)
        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });

}