function addActor() {

    const container = document.getElementById('actor-container');

    const actorBox = document.createElement('div');
    actorBox.setAttribute('class', 'actor-box');

    const newInput = document.createElement('input');
    newInput.setAttribute('type', 'text');
    newInput.setAttribute('class', 'content-input3');
    newInput.setAttribute('name', 'contentActors');
    newInput.setAttribute('placeholder', '배우 이름');

    const delBtn = document.createElement('img');
    delBtn.setAttribute('src','/images/delete-btn.png');
    delBtn.setAttribute('class', 'del-btn');

    actorBox.appendChild(newInput);
    actorBox.appendChild(delBtn);

    delBtn.onclick = function() {

        p = this.parentElement;
        p.remove(this);
    }

    container.appendChild(actorBox);
}

document.getElementById('basic-thumbnail').addEventListener('click', () => {

    document.getElementById('content-basic-thumbnail').click();

});

document.getElementById('banner-thumbnail').addEventListener('click', () => {

    document.getElementById('content-banner-thumbnail').click();

});

async function loadBasicImg(value) {

    if (value.files && value.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("basic-thumbnail").src = e.target.result;
        }
        reader.readAsDataURL(value.files[0]);
    }
}

async function loadBannerImg(value) {

    if (value.files && value.files[0]) {
        const reader = new FileReader();
        reader.onload = function (e) {
            document.getElementById("banner-thumbnail").src = e.target.result;
        }
        reader.readAsDataURL(value.files[0]);
    }
}

document.getElementById('content-create').addEventListener("keydown", evt => {
    if (evt.code === "Enter") {
        evt.preventDefault();
    }
});

document.getElementById('back-btn').addEventListener('click', () => {

    window.location.href = "/content/admin/management";

});