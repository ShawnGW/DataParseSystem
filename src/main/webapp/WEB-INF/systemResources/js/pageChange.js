function testerChange() {
    var testerStatus= document.getElementById("statusPage");
    testerStatus.style.display="block";
    var database= document.getElementById("dataBasePage");
    database.style.display="none";
    var content= document.getElementById("content");
    content.style.display="none";
}
function databaseChange() {
    var testerStatus= document.getElementById("statusPage");
    testerStatus.style.display="none";
    var database= document.getElementById("dataBasePage");
    database.style.display="block";
    var content= document.getElementById("content");
    content.style.display="none";
}
function contentChange() {
    var testerStatus= document.getElementById("statusPage");
    testerStatus.style.display="none";
    var database= document.getElementById("dataBasePage");
    database.style.display="none";
    var content= document.getElementById("content");
    content.style.display="block";
}

