$(document).ready(function() {
    // Add click event handler to anchor tags
    $("a").on("click", function(event) {
        if ($(this).text().toLowerCase() === "logout") {
            event.preventDefault();
            // Send delete request to "api/v1/login" using Fetch API
            fetch("/api/v1/login", {
                method: "DELETE"
            })
                .then(response => {
                    if (response.status===204) {
                        // Logout successful, redirect to home page
                        window.location.href = "/";
                    } else {
                        // Handle error response
                        console.error("Logout failed");
                    }
                })
                .catch(error => {
                    console.error("Error:", error);
                    // Handle any other errors
                });
        }
    });
});