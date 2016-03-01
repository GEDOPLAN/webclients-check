(function () {
    "use strict";

    angular.module("GedoplanWebClients.services.tablehelper", [])
            .service("tableHelper", function ($http, $rootScope) {
              this.parseRestSettings = function (data, settings) {
                    var restSettings = {};
                    restSettings.max = settings._iDisplayLength;
                    restSettings.start = settings._iDisplayStart;
                    restSettings.sortAttributes = [];
                    restSettings.sortDirections = [];
                    
                    settings.aaSorting.forEach(function(sortArray){
                        restSettings.sortAttributes.push(data.columns[sortArray[0]].data);
                        restSettings.sortDirections.push(sortArray[1]);
                    });
                    
                    return restSettings;
                };
                
                this.getTranslation=function() {
                    if (!this.translation){
                        this.translation=$http.get("assets/i18n/"+$rootScope.language+"_datatable.json");
                    }
                    
                    return this.translation;
                };
            });
})();


