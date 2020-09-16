Toast = {
  success: function (message) {
    Swal.fire({
      position: 'top-top',
      icon: 'success',
      title: message,
      showConfirmButton: false,
      timer: 3000
    })
  },

  error: function (message) {
    Swal.fire({
      position: 'top-top',
      icon: 'error',
      title: message,
      showConfirmButton: false,
      timer: 3000
    })
  },

  warning: function (message) {
    Swal.fire({
      position: 'top-top',
      icon: 'warning',
      title: message,
      showConfirmButton: false,
      timer: 3000
    })
  }
};
