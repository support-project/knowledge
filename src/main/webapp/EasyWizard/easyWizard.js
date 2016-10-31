 $.fn.wizard = function (config) {
     if (!config) {
         config = {};
     };
     var validateFunc = function (step) {
         return new Promise(function (resolve) {
             console.log(step);
             return resolve(true);
         });
     };
     var containerSelector = config.containerSelector || ".wizard-content";
     var stepSelector = config.stepSelector || ".wizard-step";
     var steps = $(this).find(containerSelector + " " + stepSelector);
     var stepCount = steps.size();
     var exitText = config.exit || '<i class="fa fa-stop-circle" aria-hidden="true"></i> Exit';
     var backText = config.back || '<i class="fa fa-backward" aria-hidden="true"></i> Back';
     var nextText = config.next || 'Next <i class="fa fa-forward" aria-hidden="true"></i>';
     var finishText = config.finish || '<i class="fa fa-gavel" aria-hidden="true"></i> Finish';
     var isModal = config.isModal || true;
     var validateNext = config.validateNext || validateFunc;
     var validateFinish = config.validateFinish || validateFunc;
     // ////////////////////
     var step = 1;
     var container = $(this).find(containerSelector);
     steps.hide();
     $(steps[0]).show();
     if (isModal) {
         $(this).on('hidden.bs.modal', function () {
             step = 1;
             $($(containerSelector + " .wizard-steps-panel .step-number")
                     .removeClass("done")
                     .removeClass("doing")[0])
                 .toggleClass("doing");

             $($(containerSelector + " .wizard-step")
                     .hide()[0])
                 .show();

             btnBack.hide();
             btnExit.show();
             btnFinish.hide();
             btnNext.show();

         });
     };
     $(this).find(".wizard-steps-panel").remove();
     container.prepend('<div class="wizard-steps-panel steps-quantity-' + stepCount + '"></div>');
     var stepsPanel = $(this).find(".wizard-steps-panel");
     for (s = 1; s <= stepCount; s++) {
         stepsPanel.append('<div class="step-number step-' + s + '"><div class="number">' + s + '</div></div>');
     }
     $(this).find(".wizard-steps-panel .step-" + step).toggleClass("doing");
     // ////////////////////
     var contentForModal = "";
     if (isModal) {
         contentForModal = ' data-dismiss="modal"';
     }
     var btns = "";
     btns += '<button type="button" class="btn btn-default wizard-button-exit"' + contentForModal + '>' + exitText + '</button>';
     btns += '<button type="button" class="btn btn-default wizard-button-back">' + backText + '</button>';
     btns += '<button type="button" class="btn btn-default wizard-button-next">' + nextText + '</button>';
     btns += '<button type="button" class="btn btn-primary wizard-button-finish" ' + contentForModal + '>' + finishText + '</button>';
     $(this).find(".wizard-buttons").html("");
     $(this).find(".wizard-buttons").append(btns);
     var btnExit = $(this).find(".wizard-button-exit");
     var btnBack = $(this).find(".wizard-button-back");
     var btnFinish = $(this).find(".wizard-button-finish");
     var btnNext = $(this).find(".wizard-button-next");

     btnNext.on("click", function () {
         btnNext.html('<i class="fa fa-refresh fa-spin fa-1x fa-fw"></i>' + nextText);
         validateNext(step, steps[step - 1])
         .then(function(result) {
             btnNext.html(nextText);
             if (!result) {
                 return;
             }
             $(container).find(".wizard-steps-panel .step-" + step).toggleClass("doing").toggleClass("done");
             step++;
             steps.hide();
             $(steps[step - 1]).show();
             $(container).find(".wizard-steps-panel .step-" + step).toggleClass("doing");
             if (step == stepCount) {
                 btnFinish.show();
                 btnNext.hide();
             }
             btnExit.hide();
             btnBack.show();
         });
     });

     btnBack.on("click", function () {
         $(container).find(".wizard-steps-panel .step-" + step).toggleClass("doing");
         step--;
         steps.hide();
         $(steps[step - 1]).show();
         $(container).find(".wizard-steps-panel .step-" + step).toggleClass("doing").toggleClass("done");
         if (step == 1) {
             btnBack.hide();
             btnExit.show();
         }
         btnFinish.hide();
         btnNext.show();
     });

     btnFinish.on("click", function () {
         validateFinish(step, steps[step - 1])
         .then(function(result) {
             if (!result) {
                 return;
             }
             if (!!config.onfinish) {
                 config.onfinish();
             }
         });
     });

     btnBack.hide();
     btnFinish.hide();
     return this;
 };