function sendData() {
  const name = document.getElementById('name').value;
  const gender = document.querySelector('input[name="gender"]:checked').value;
  const year = document.getElementById('year').value;
  const month = document.getElementById('month').value;
  const day = document.getElementById('day').value;

  const interests = Array.from(document.querySelectorAll('input[name="interest"]:checked'))
    .map((el) => el.value);

  const userData = {
    name: name,
    gender: gender,
    year: year,
    month: month,
    day: day,
    interest: interests,
  };

  axios
    .post(`/form/axios/vo`, userData)
    .then((res) => {
      console.log('서버 :', res.data);
    })
    .catch((error) => {
      console.error('오류 :', error);
    });
}