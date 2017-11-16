tinymce.init({
    selector: '.wysiwyg',

    plugins: 'image link media advlist lists textcolor wordcount imagetools contextmenu colorpicker textpattern',
    toolbar: 'formatselect | bold italic strikethrough forecolor backcolor | link image | alignleft aligncenter alignright alignjustify  | numlist bullist outdent indent  | removeformat',
    automatic_uploads: true,
    images_upload_url: '/upload/image',
    images_reuse_filename: true
});