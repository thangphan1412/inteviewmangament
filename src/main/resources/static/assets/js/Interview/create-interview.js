$(document).ready(function () {
    $('#buttonSubmit').click(function (e) {
        e.preventDefault();
        var interview = {
            "scheduleTitle": $("#scheduleTitle").val() === "" ? null : $("#scheduleTitle").val(),
            "assignee": $("#assignee").val() === "" ? null : $("#assignee").val().join(","),
            "candidateEmail": $("#candidateName").val() === "" ? null : $("#candidateName").val(),
            "location": $("#location").val(),
            "scheduleDate": $("#scheduleDate").val(),
            "scheduleFrom": $("#scheduleFrom").val(),
            "scheduleTo": $("#scheduleTo").val(),
            "recruiterOwnerAccount": $("#recruiterOwner").val(),
            "note": $("#exampleFormControlTextarea1").val(),
            "meetingId": $("#meetingId").val()
        };

        $.validator.addMethod("validateDueDate", function (value, element) {
            return this.optional(element) || dueDate >= curDate;
        }, "ScheduleDate must greater than Today");

        $.validator.addMethod("validateTime", function (value, element) {
            return this.optional(element) || endTime >= startTime;
        }, "End Time must greater than Start Time");

        let today = new Date();
        let date = "'" + today.getFullYear() + '-' + (today.getMonth() + 1) + '-' + today.getDate() + "'";
        const curDate = new Date(date);
        const dueDate = new Date($("#scheduleDate").val())

        const scheduleFrom = document.getElementById("scheduleFrom").value; // Lấy giá trị giờ từ input
        const [hours, minutes] = scheduleFrom.split(":"); // Phân tích giá trị giờ thành giờ và phút
        const startTime = new Date(today.getFullYear(), today.getMonth(), today.getDate(), hours, minutes); // Tạo đối tượng Date mới với giờ và phút được lấy từ input

        const scheduleTo = document.getElementById("scheduleTo").value; // Lấy giá trị giờ từ input
        const [hoursTo, minutesTo] = scheduleTo.split(":"); // Phân tích giá trị giờ thành giờ và phút
        const endTime = new Date(today.getFullYear(), today.getMonth(), today.getDate(), hoursTo, minutesTo); // Tạo đối tượng Date mới với giờ và phút được lấy từ input

        console.log(startTime);
        console.log(endTime);
        $("#formValidate").validate({
            rules: {
                scheduleTitle: {
                    required: true,
                },
                assignee: {
                    required: true,
                },
                candidateName: {
                    required: true,
                },
                scheduleDate: {
                    required: true,
                    validateDueDate: true
                },
                scheduleFrom: {
                    validateTime: true,
                    required: true,
                },
                scheduleTo: {
                    validateTime: true,
                    required: true,
                },
                recruiterOwner: {
                    required: true,
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
                url: '/Interview/createInterview',
                contentType: "application/json",
                data: JSON.stringify(interview),
                success: function (data) {
                    // Xử lý kết quả trả về khi server xử lý thành công
                    Swal.close();
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
                        title: 'Create candidate successfully'
                    }).then(() => {
                        location.reload();
                    });

                },
                error: function (jqXHR, textStatus, errorThrown) {
                    Swal.close();

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
                        title: jqXHR.responseText
                    })

                }
            });
        }

    });
});