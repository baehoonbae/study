    // 모달 열기 함수
    function openModal() {
        $('#passwordModal').modal('show');
    }
    // 모달 닫기 함수
    function closeModal() {
        $('#passwordModal').modal('hide');
        $('#confirmButton').removeAttr('data-action');
        $('#password').val('');
    }

    $('#passwordModal').on('shown.bs.modal', function () {
        $('#password').focus();
    });

    // 수정 버튼 클릭 시
    $('#updateButton').click(function() {
        $('#confirmButton').attr('data-action', 'update');
        openModal();
    });

    // 삭제 버튼 클릭 시
    $('#deleteButton').click(function() {
        $('#confirmButton').attr('data-action', 'delete');
        openModal();
    });

    // 모달에서 확인 버튼 클릭 시 처리하는 함수
    $('#confirmButton').click(function() {
        var postId = $('#contentForm').attr('action').split('/')[2];
        var enteredPassword = $('#password').val();
        var action = $(this).data('action');

        if (action === 'update') {
            $.ajax({
                url: '/post/' + postId + '/validate-password',
                type: 'POST',
                data: {
                    id: postId,
                    password: enteredPassword,
                },
                success: function(response) {
                    window.location.href = '/post/' + postId + '/edit';
                },
                error: function(xhr, status, error) {
                    if (xhr.status === 401) {
                       alert('비밀번호가 일치하지 않습니다.');
                    } else {
                       alert('서버 오류가 발생했습니다.');
                    }
                    closeModal();
                }
            });
        } else {
            $.ajax({
                url: '/post/' + postId + '/delete',
                type: 'POST',
                data: {
                    id: postId,
                    password: enteredPassword,
                },
                success: function(response) {
                    alert('게시물이 성공적으로 삭제되었습니다.');
                    window.location.href = '/';
                },
                error: function(xhr, status, error) {
                    if (xhr.status === 401) {
                       alert('비밀번호가 일치하지 않습니다.');
                    } else {
                       alert('서버 오류가 발생했습니다.');
                    }
                    closeModal();
                }
            });
        }
        // 모달을 닫습니다.
        closeModal();

        $(this).removeData('action');
    });
