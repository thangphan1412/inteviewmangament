$("#pdf-file").on("change", function () {
    var fileInput = document.getElementById("pdf-file");
    var file = fileInput.files[0];
    var formData = new FormData();
    formData.append("file", file);
    console.log(formData);
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
        url: "/upload/drive",
        type: "POST",
        data: formData,
        processData: false,
        contentType: false,
        success: function (data) {
            Swal.close();
            Swal.fire('Success', 'Upload file successful!', 'success');
            console.log("Upload successful");
            console.log(data);
            $("#cvAttachment").val(data);
        },
        error: function (xhr, status, error) {
            Swal.close();
            Swal.fire('Failed', 'Upload file failed!', 'error');
            console.log("Upload failed: " + error);
        }
    });
})
