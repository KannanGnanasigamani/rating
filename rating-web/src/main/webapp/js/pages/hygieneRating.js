/*
*****local functions start with underscore (ex: _getUrl)
*****Implements the objects "ratingText", "models" and "percentage".
*****Scoped objects
*/

var ratingText = {
    EXEMPT: 'Exempt',
    PASS: 'Pass',
    NEEDS_IMPROVEMENT: 'Improvement Required',
    EAT_SAFE: 'Pass and Eat Safe',
    STAR: '-star',
    region: {
        SCOTLAND: 'SCOTLAND'
    },
    message: {
        ERROR_MESSAGE : 'Please select an authority to see the ratings!'
    },
    messageDirection: {
        RIGHT: 'right'
    }
};

var models = {
    model: null,
    authorities: null,
    establishments: [],
    authorityRegion: null,
    isScotland: false,
    loaderTemplate: null,

    loadAuthorities: function() {
        var that = this;
        this._displayLoader($('#authoritiesContainer'));

        getJSONData(contextPath + "/localAuthoritiesList.json", false, function(authorities) {
            that._populateAuthorities(authorities);
        },
        function(response) {
            messageModal.error(response.responseText);
        });
    },

    _populateAuthorities: function(data) {
        var authoritiesTemplate = Handlebars.compile($('#authoritiesSelectTemplate').html());
        $('#authoritiesContainer').html(authoritiesTemplate(data));
        $('#authoritiesSelect').selectpicker('refresh');
    },

    _authorityChanged: function() {
        if(this._isValidAuthority()) {
            var authorityId = parseInt($('#authoritiesSelect').val());
            this.authorityRegion = $('#authoritiesSelect option:selected').attr('region').toUpperCase();
            this._setRegion();
            var that = this;
            this._displayLoader($('#percentageContainer'));
            getJSONData(contextPath + "/establishments.json?authorityId="+ authorityId, false, function(establishments) {
                    that.establishments = establishments;
                    that._populateEstablishments();
                },
                function(response) {
                    $('#percentageContainer').html('');
                    messageModal.error(response.responseText);
                });

        } else {
            $('#percentageContainer').html('');
            popups.show('#authoritiesSelect', ratingText.message.ERROR_MESSAGE, ratingText.messageDirection.RIGHT);
        }
    },

    _displayLoader: function (selector) {
        selector.html(models.loaderTemplate);
    },

    _isValidAuthority: function() {
        return $('#authoritiesSelect').val() > -1;
    },

    _populateEstablishments: function() {
        var percentageTemplate = Handlebars.compile($('#percentageTemplate').html()),
            ratingsInPercentage = this.isScotland ? percentage.calculatePercentage(this.isScotland): this._sortPercentage(percentage.calculatePercentage(this.isScotland));
        $('#percentageContainer').html(percentageTemplate(ratingsInPercentage));
    },

    _setRegion: function() {
        this.isScotland = this.authorityRegion === ratingText.region.SCOTLAND;
    },

    _sortPercentage: function(ratings) {
        //sort descending
        return ratings.sort( function ( a, b ) { return b.rating - a.rating; } );
    },

    init: function() {
        $('body').on('change', '#authoritiesSelect', $.proxy(this._authorityChanged, this));
        this.loaderTemplate = Handlebars.compile($('#loaderTemplate').html());
    }

};

var percentage = {

    calculatePercentage: function(isScotland) {
        var ratings = (isScotland)? this._ratingFilterForScotland( models.establishments.establishments ): this._ratingFilter( models.establishments.establishments ),
            establishmentLength = models.establishments.establishments.length,
            percentage = {},
            percentageArray = [];
        for(item in ratings) {
            var starPerc = Math.round((ratings[item])/establishmentLength * 100) + "%";
            percentage[item] = starPerc;
            percentageArray.push(
                {
                    rating: item,
                    percentage: starPerc
                }
            );
        }

        return percentageArray;
    },

    _ratingFilter: function(establishmentList) {

        var fiveStarCount = 0,
            fourStarCount = 0,
            threeStarCount = 0,
            twoStarCount = 0,
            oneStarCount = 0,
            noStarCount = 0;

        establishmentList.forEach(function(establishment) {
            switch(establishment.RatingValue) {
                case "5":
                    fiveStarCount += 1;
                    break;
                case "4":
                    fourStarCount += 1;
                    break;
                case "3":
                    threeStarCount += 1;
                    break;
                case "2":
                    twoStarCount += 1;
                    break;
                case "1":
                    oneStarCount += 1;
                    break;
                case ratingText.EXEMPT:
                    noStarCount += 1;
                    break;
            }

        });

        return {
            5: fiveStarCount,
            4: fourStarCount,
            3: threeStarCount,
            2: twoStarCount,
            1: oneStarCount,
            0: noStarCount
        };
    },


    _ratingFilterForScotland: function(establishmentList) {

        var passCount = 0,
            improveReqCount = 0;

        establishmentList.forEach(function(establishment) {
            switch(establishment.RatingValue) {
                case ratingText.PASS:
                    passCount += 1;
                    break;
                case ratingText.EAT_SAFE:
                    passCount += 1;
                    break;
                case ratingText.NEEDS_IMPROVEMENT:
                    improveReqCount += 1;
                    break;
            }
        });

        return {
            "Pass": passCount,
            "Needs Improvement": improveReqCount,
        };
    }

};


$(document).ready(function() {
    models.init();
    popups.init();
    models.loadAuthorities();

    Handlebars.registerHelper('setTitle', function(value) {
        return models.isScotland ? value : new Handlebars.SafeString(value == 0 ? ratingText.EXEMPT: value + ratingText.STAR );
    });

    Handlebars.registerHelper('setRegion', function() {
        return models.authorityRegion;
    });

});

function getJSONData(url, cache, successCallback, errorCallback) {
    $.get({
        dataType: 'json',
        url: url,
        cache: cache   /* needed for IE, which caches by default. */
    })
    .done(function(data){
        successCallback(data);
    })
    .fail(function(error) {
        if(error.status === 500) {
            console.log("Internal Server Error");
            messageModal.error("Unknown Request!");
            $('#loader').hide();
        } else {
            errorCallback(error);
        }
    });
}
