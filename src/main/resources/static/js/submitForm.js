function submitForm(location) {
    const form = document.getElementById("form");
    fetch(form.action, {
        method: "POST",
        body: new FormData(form)
    })
        .then(async response => {
            if (response.ok) {

                window.location.href = location;
            } else if (response.status === 400) {
                const res = response.clone();
                let message =await response.text()
                console.log(message)
                if(message.includes('message')){
                    message = await res.json();
                }
                document.getElementById("errorDiv").innerText =
                    message.message===undefined ? message : message.message;

                document.getElementById("errorDiv").style.display = "block";

            }
        })
        .catch(error => {
            console.error("Error:", error);
            // Handle any other errors here
        });
}