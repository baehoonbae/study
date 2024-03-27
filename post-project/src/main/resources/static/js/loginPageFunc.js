$(document).ready(function() {
    $('#loginForm').submit(function(event) {
        event.preventDefault();

        var username = $('#username').val();
        var password = $('#password').val();

        $.ajax({
            url: '/login',
            type: 'post',
            data: {username: username, password: password},
            success: function(response) {
                window.location.href = '/';
            },
            error: function(xhr, status, error) {
                alert('아이디 혹은 비밀번호를 확인해 주세요.');
            }
        });
    });
});