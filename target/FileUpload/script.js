"use strict";
window.onload = function () {


    var field = document.getElementById("files");
    var status = document.getElementById("status");

    function upload() {

        var file = field.files[0];
        var formData = new FormData();
        formData.append("file", file);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "");
        xhr.onreadystatechange = function () {
            console.log(this.readyState);
            console.log(this.statusText);
            console.log(this.status);
            console.log(this.responseText);
            status.innerHTML = this.responseText;
        };
        xhr.send(formData);
    }

    function uploadUsingJQ() {

        var file = field.files[0];
        var formData = new FormData();
        formData.append("file", file);
        var ajaxUrl = "";


        $.ajax({
            url:"/",
            data: formData,
            processData: false,
            type: 'POST',
            contentType: false,
            mimeType: 'multipart/form-data',
            success: function ( data ) {
                console.log( data );
                status.innerHTML = data;
            }
        });
    }

    var button = document.getElementById("button");
    button.addEventListener("click",function () {
        upload();
    });


    var buttonJQ = document.getElementById("buttonJQ");
    buttonJQ.addEventListener("click",function () {
        uploadUsingJQ();
    })
}