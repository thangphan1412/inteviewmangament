$(function () {
    'use strict'

    if ($(".js-example-basic-single").length) {
        $(".js-example-basic-single").select2();
    }
    if ($(".js-example-basic-multiple").length) {
        $(".js-example-basic-multiple").select2();
    }

    if ($(".js-example-basic-multiple-modal").length) {
        $(".js-example-basic-multiple-modal").select2({
            dropdownParent: $("#myModal")
        });
    }

    if ($(".js-example-basic-single-modal").length) {
        $(".js-example-basic-single-modal").select2({
            dropdownParent: $("#myModal")
        });
    }


    // Offer
    if ($("#candidateSelect").length) {
        $("#candidateSelect").select2({
            placeholder: "Select candidate"
        });
    }
    if ($("#contractType").length) {
        $("#contractType").select2({
            placeholder: "Select contract type"
        });
    }
    if ($("#positionId").length) {
        $("#positionId").select2({
            placeholder: "Select position"
        });
    }

    if ($("#level").length) {
        $("#level").select2({
            placeholder: "Select level"
        });
    }
    if ($("#approveBy").length) {
        $("#approveBy").select2({
            placeholder: "Select approved by"
        });
    }
    if ($("#department").length) {
        $("#department").select2({
            placeholder: "Select department"
        });
    }
    if ($("#candidateStatus").length) {
        $("#candidateStatus").select2({
            placeholder: "Select status"
        });
    }

    if ($("#recruiterOwner").length) {
        $("#recruiterOwner").select2({
            placeholder: "Select recruiter owner"
        });
    }

    if ($("#interviewer").length) {
        $("#interviewer").select2({
            placeholder: "Select interview",
            disabled: true
        });
    }
});