var md5 = {
    init: function () {
        var _this = this;
        $('#text').on('input', function () {
            _this.submit($('#text').val());
        });
        $('#btn_clip').on('click', function () {
            _this.submit($('#text').val());
        });
    },

    submit : function (text) {
        $.ajax({
            type: 'GET',
            url: '/api/crypto/md5?text=' + text,
            dataType: 'json',
            contentType:'application/json; charset=utf-8',
        }).done(function(json) {
            console.log(json.data)
            $('#hash-text').text(json.data)
        }).fail(function (error) {
            alert(JSON.stringify(error.message));
        });
    }
};

md5.init();
md5.submit('');