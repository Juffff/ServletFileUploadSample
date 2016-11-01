"use strict";
window.onload = function () {


    var field = document.getElementById("files");
    var status = document.getElementById("status");

    function upload() {

        var file = field.files[0];
        var formData = new FormData();
        formData.append("file", file);
        var xhr = new XMLHttpRequest();
        xhr.open("POST", "upload");
        xhr.onreadystatechange = function () {
            console.log(this.readyState);
            console.log(this.statusText);
            console.log(this.status);
            console.log(this.responseText);
            status.innerHTML = this.responseText;
        };
        xhr.send(formData);
    }

    var button = document.getElementById("button");
    button.addEventListener("click",function () {
        upload();
    })
}