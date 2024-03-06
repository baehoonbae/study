   $(document).ready(function(){
       $('#cancelButton').click(function(e) {
               var postId = $('#editForm').attr('action').split('/')[2];
               e.preventDefault(); // 기본 동작 중단
               window.location.href = '/post/' + postId; // 홈으로 이동
       });

       $('#editForm').submit(function(event){
           var postId = $('#editForm').attr('action').split('/')[2];
           var newTitle = $('#title').val();
           var newTexts = $('#content').val();

           $.ajax({
               url: '/post/' + postId + '/edit',
               type: 'POST',
               data: {
                   id: postId,
                   title: newTitle,
                   content: newTexts
               },
               success: function(response) {
                   alert('게시물이 성공적으로 수정되었습니다.');
                   window.location.href = '/post/' + postId;
               },
               error: function(xhr, status, error) {
                   alert('서버 오류가 발생했습니다.');
               }
           });
           event.preventDefault(); // 기본 폼 제출 동작 중지
       });
   });

