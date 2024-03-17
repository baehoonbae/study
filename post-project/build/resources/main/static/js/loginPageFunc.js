document.getElementById("loginForm").addEventListener("submit", function(event) {
  event.preventDefault(); // 폼 제출을 막습니다.

  // 입력된 아이디와 비밀번호를 가져옵니다.
  var username = document.getElementById("username").value.trim();
  var password = document.getElementById("password").value.trim();

  // 서버에 로그인 요청을 보냅니다.
  fetch("/post/login", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify({
      username: username,
      password: password
    })
  })
  .then(response => {
    if (response.ok) {
      // 로그인 성공 시 페이지를 다시 로드합니다.
      window.location.reload();
    } else {
      // 로그인 실패 시 오류 메시지를 표시합니다.
      document.getElementById("errorMessage").textContent = "아이디 혹은 비밀번호가 일치하지 않습니다.";
    }
  })
  .catch(error => console.error("로그인 오류:", error));
});