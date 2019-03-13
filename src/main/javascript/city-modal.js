
(function ($) {
  function initModalEventHandlers() {
    // loads content into .modal-body when the modal is being shown
    var $cityModal = $('#cityModal');
    $cityModal.on('show.bs.modal', function (e) {
      var $modal = $(e.target);
      var trigger = e.relatedTarget;
      var location = trigger.getAttribute('href');
      $modal.find('.modal-title').text(trigger.innerText);
      $modal.find('.modal-body').load(location);
    });

    // Closes the modal when the user clicks the cancel button instead of following its link
    $('.modal').on('click', '.btn-cancel', function (e) {
      e.preventDefault();
      $(e.delegateTarget).modal('hide');
    });

    // Make modals draggable
    $('.modal-dialog').draggable({
      handle: '.modal-header'
    });
  }

  initModalEventHandlers();
})(jQuery);