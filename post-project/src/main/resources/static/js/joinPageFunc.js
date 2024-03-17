$(document).ready(function() {
    // 회원가입 활성화 상태
    var joinActivation = true;

    // 회원가입 버튼 요소
    var joinButton = document.getElementById('joinButton');

    // 회원가입 버튼의 클릭 이벤트 핸들러 추가
    joinButton.addEventListener('click', function(event) {
        // 만약 회원가입 활성화 상태가 false라면 클릭을 못하게 막는다
        if (!joinActivation) {
            event.preventDefault();
        }
    });

    // joinActivation 업데이트 및 회원가입 버튼 활성화/비활성화 함수
    function updateJoinActivation() {
        // 각 error 가 출력되었는지 확인
        var usernameError = document.getElementById('username_error');
        var nicknameError = document.getElementById('nickname_error');
        var passwordError = document.getElementById('password_error');
        var confirmPasswordError = document.getElementById('confirmPassword_error');

        // 에러 메시지가 출력되었는지 확인하고 joinActivation을 업데이트함
        if (usernameError.innerHTML || nicknameError.innerHTML || passwordError.innerHTML || confirmPasswordError.innerHTML) {
            joinActivation = false; // 한 개라도 에러 메시지가 출력되었다면 회원가입 비활성화
        } else {
            joinActivation = true; // 에러 메시지가 없다면 회원가입 활성화
        }

        // 회원가입 버튼을 활성화 또는 비활성화
        if (joinActivation) {
            joinButton.removeAttribute('disabled'); // 활성화
        } else {
            joinButton.setAttribute('disabled', 'disabled'); // 비활성화
        }
    }

    // 아이디 중복 및 입력 확인
    $('#username').blur(function(){
        var username = $(this).val();
        if (!username) {
            $('#username_error').html('* 아이디: 필수 정보입니다.');
        } else {
            username = $(this).val();
            $.ajax({
                url: '/check-username',
                type: 'post',
                data: {username: username},
                success: function(usernameResponse){
                     if (usernameResponse) {
                         $('#username_error').html(usernameResponse); // 메시지 출력
                     } else {
                         $('#username_error').html(''); // 메시지 비우기
                     }
                     // 에러 메시지가 변경될 때마다 joinActivation 업데이트
                     updateJoinActivation();
                }
            });
        }
    });

    // 패스워드 입력 확인
    $('#password').blur(function(){
        var password = $(this).val();
        if (!password) {
            $('#password_error').html('* 비밀번호: 필수 정보입니다.');
        } else{
            $('#password_error').html('');
        }
        // 에러 메시지가 변경될 때마다 joinActivation 업데이트
        updateJoinActivation();
    });

    // 비밀번호 일치 여부 확인
    $('#confirmPassword').blur(function(){
        var password1 = $('#password').val();
        var password2 = $(this).val();
        if (password1 !== password2) {
            $('#confirmPassword_error').html('* 비밀번호와 일치하지 않습니다.');
        } else {
            $('#confirmPassword_error').html('');
        }
        // 에러 메시지가 변경될 때마다 joinActivation 업데이트
        updateJoinActivation();
    });

    // 닉네임 중복 및 입력 확인
    $('#nickname').blur(function(){
        var nickname = $(this).val();
        if (!nickname) {
            $('#nickname_error').html('* 닉네임: 필수 정보입니다.');
        } else {
            nickname = $(this).val();
            $.ajax({
                url: '/check-nickname',
                type: 'post',
                data: {nickname: nickname},
                success: function(nicknameResponse){
                    if (nicknameResponse) {
                        $('#nickname_error').html(nicknameResponse); // 메시지 출력
                    } else {
                        $('#nickname_error').html(''); // 메시지 비우기
                    }
                    // 에러 메시지가 변경될 때마다 joinActivation 업데이트
                    updateJoinActivation();
                }
            });
        }
    });

});
