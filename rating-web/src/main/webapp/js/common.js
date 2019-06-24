/*
*****local functions start with underscore (ex: _getUrl)
*****Implements the common objects "messageModal" and "popups".
 */


var messageModal = {
    error: function(message) {
        this._show(message, 'Error!');
    },

    _show: function(message, title) {
        $('#messageModal .modal-title').text(title);
        $('#messageModal .modal-body').text(message);
        $('#messageModal').modal({
            show: true,
            backdrop: 'static'
        });
    }
};

var popups = {
    _timeouts: {},
    _popOverHtml: null,

    show: function(selector, message, orientation) {
        var cell = $(selector);
        if (this._timeouts[selector]) {
            return;
        }
        if (orientation === undefined) {
            orientation = 'right';
        }
        cell.popover({content: message, template: this._popOverHtml, placement: orientation, container: 'body'});
        cell.popover('show');

        var that = this;
        this._timeouts[selector] = setTimeout(function() {
            that.hide(selector);
        }, 2200);
    },

    hide: function(selector) {
        if (this._timeouts[selector]) {
            var cell = $(selector);
            cell.popover('destroy');
            delete this._timeouts[selector];
        }
    },

    init: function() {
        this._popOverHtml = $('#validationPopoverTemplate').html();
    }
};

