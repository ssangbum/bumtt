document.getElementById("create-content").addEventListener("click", () => {

    window.location.href = "/content/admin/create";
})

function contentDetail(contentNo) {

    window.location.href = "/content/admin/detail/" + contentNo;
}

function updateContentStatus (contentNo, contentStatusYn){

    if(confirm("활성화 하시겠습니까?")) {

        let requestData = {
            method: 'post',
            body: JSON.stringify({
                contentNo,
                contentStatusYn
            }),
            headers: {
                'Content-Type': 'application/json'
            }
        };

        fetch('/content/update/contentStatus', requestData)
            .then(response => response.text())
            .then(data=> {

                alert(data);
                window.location.reload();
            }).catch(err => {
                console.log(err)
        });

    }
}
