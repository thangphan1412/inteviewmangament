function ViewInterviewDetails(id) {
    $.ajax({
        type: 'GET',
        url: '/Interview/' + id,
        contentType: "application/json",
        success: function (data) {
            // Xử lý kết quả trả về khi server xử lý thành công
            $("#scheduleId").val(data.scheduleId);
            $("#scheduleTitle").val(data.scheduleTitle);
            let assigneeArray = data.assignee.split(",");
            $("#assignee").val(assigneeArray).trigger("change");
            $("#candidateName").val(data.candidateName.fullname + " (" + data.candidateEmail + ")");
            $("#location").val(data.location);
            $("#scheduleDate").val(data.scheduleDate);
            $("#scheduleFrom").val(data.scheduleFrom);
            $("#scheduleTo").val(data.scheduleTo);

            let recruiterOwnerArray = data.recruiterOwnerAccount;
            $("#recruiterOwners").val(recruiterOwnerArray).trigger("change");

            $("#exampleFormControlTextarea1").val(data.note);
            $("#meetingId").val(data.meetingId);

            $("#interviewScheduleResult").val(data.interviewScheduleResult).trigger("change");
            $("#interviewScheduleStatus").val(data.interviewScheduleStatus);


        },
        error: function (jqXHR, textStatus, errorThrown) {

        }
    });

}