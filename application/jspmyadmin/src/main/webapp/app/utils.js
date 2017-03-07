'use strict';
var IdHelper = {
    id: 1,
    name: 'jma_id_'
};
IdHelper.generate = function () {
    this.id++;
    return this.name + this.id;
};

var EncryptionHelper = {
    encode: function (string) {
        var encode = CryptoJS.AES.encrypt(string, aaaaaaaa);
        return Base64.encodeURI(encode.toString());
    },
    decode: function (string) {
        var decode = CryptoJS.AES.decrypt(Base64.decode(string), aaaaaaaa);
        return decode.toString(CryptoJS.enc.Utf8);
    }
};

var ByteUtils = {
    fromBytes: function (original) {
        if (original > 1024) {
            original = original / 1024;
            if (original > 1024) {
                original = original / 1024;
                if (original > 1024) {
                    return original.toFixed(2) + ' GB';
                } else {
                    return original.toFixed(2) + ' MB';
                }
            } else {
                return original.toFixed(2) + ' KB';
            }
        } else {
            return original + ' B';
        }
    }
};

function scrollTo(selector) {
    $('#main-content').animate({scrollTop: $(selector).offset().bottom}, 'slow');
}

function scrollToBottom() {
    var content = $('#main-content');
    content.animate({scrollTop: $(document).height()}, "slow");
}

function showWaiting() {
    $('#sidebar-loading-dialog').show();
}

function hideWaiting() {
    $('#sidebar-loading-dialog').hide();
}

$(function () {
    $('#sidebar-error-close').on('click', function () {
        $('#sidebar-error-dialog').hide();
        $('#sidebar-error-msg').text('');
    });

    $('#sidebar-success-close').on('click', function () {
        $('#sidebar-success-dialog').hide();
        $('#sidebar-success-msg').text('');
    });
});

var ErrorDialog = {
    show: function (msg) {
        $('#error-content').text(msg);
        $('#error-dialog').show();
    },
    hide: function () {
        $('#error-dialog').hide();
    }
};
$(function () {
    $('#error-close').on('click', function () {
        ErrorDialog.hide();
    });
});