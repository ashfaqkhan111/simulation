const loginForm = document.getElementById("loginForm");
const identifierInput = document.getElementById("identifier");
const passwordInput = document.getElementById("password");

const loginButton = document.getElementById("loginButton");
const buttonText = document.getElementById("buttonText");
const buttonSpinner = document.getElementById("buttonSpinner");
const togglePassword = document.getElementById("togglePassword");
const messageBox = document.getElementById("message");

function showMessage(message, type) {
    messageBox.textContent = message;
    messageBox.className = `message ${type}`;
    messageBox.hidden = false;
}

function setLoading(isLoading) {
    loginButton.disabled = isLoading;
    buttonText.textContent = isLoading ? "Signing in..." : "Sign in";
    buttonSpinner.hidden = !isLoading;
}

togglePassword.addEventListener("click", () => {
    const isPassword = passwordInput.type === "password";

    passwordInput.type = isPassword ? "text" : "password";
    togglePassword.textContent = isPassword ? "Hide" : "Show";
    togglePassword.setAttribute(
        "aria-label",
        isPassword ? "Hide password" : "Show password"
    );
});

loginForm.addEventListener("submit", async (event) => {
    event.preventDefault();

    messageBox.hidden = true;
    setLoading(true);

    // Match the JSON fields expected by LoginRequest.
    const loginData = {
        identifier: identifierInput.value.trim(),
        password: passwordInput.value
    };

    try {
        // Relative URL: uses the same Spring Boot server serving this page.
        const response = await fetch("/api/auth/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json"
            },
            body: JSON.stringify(loginData)
        });

        const responseText = await response.text();
        let data = {};

        if (responseText) {
            try {
                data = JSON.parse(responseText);
            } catch {
                data = {};
            }
        }

        if (!response.ok) {
            if (response.status === 401) {
                throw new Error("Incorrect username/email or password.");
            }

            if (response.status === 403) {
                throw new Error(
                    "Access denied. Check your account permissions."
                );
            }

            if (response.status >= 500) {
                throw new Error(
                    "The server encountered an error. Please try again."
                );
            }

            throw new Error(
                data.message || data.error || `Login failed (${response.status}).`
            );
        }

        // Match the latest LoginResponse DTO.
        if (!data.accessToken) {
            throw new Error(
                "The server response did not contain accessToken. " +
                "Check your LoginResponse DTO."
            );
        }

        // Keep tokens for this browser tab/session, not permanently.
        sessionStorage.setItem("accessToken", data.accessToken);

        if (data.refreshToken) {
            sessionStorage.setItem("refreshToken", data.refreshToken);
        }

        sessionStorage.setItem("tokenType", data.tokenType || "Bearer");

        if (data.expiresIn != null) {
            sessionStorage.setItem("expiresIn", String(data.expiresIn));
        }

        passwordInput.value = "";

        showMessage(
            "Login successful! Your authentication token is ready. " +
            "The dashboard will be connected in the next step.",
            "success"
        );

    } catch (error) {
        if (error instanceof TypeError) {
            showMessage(
                "Cannot connect to the backend. Make sure Spring Boot is running.",
                "error"
            );
        } else {
            showMessage(error.message, "error");
        }
    } finally {
        setLoading(false);
    }
});