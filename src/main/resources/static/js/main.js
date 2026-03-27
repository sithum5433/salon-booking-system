function updateDigitalClock() {
    const clockElement = document.getElementById('digitalClock');
    if (!clockElement) return;

    const now = new Date();
    clockElement.textContent = now.toLocaleString();
}

function updateClosingCountdown() {
    const countdownElement = document.getElementById('closeCountdown');
    if (!countdownElement) return;

    const now = new Date();
    const closeTime = new Date();
    closeTime.setHours(20, 0, 0, 0); // 8:00 PM

    const difference = closeTime.getTime() - now.getTime();

    if (difference <= 0) {
        countdownElement.textContent = 'Salon closed for today';
        return;
    }

    const hours = Math.floor(difference / (1000 * 60 * 60));
    const minutes = Math.floor((difference % (1000 * 60 * 60)) / (1000 * 60));
    const seconds = Math.floor((difference % (1000 * 60)) / 1000);

    countdownElement.textContent = `${hours}h ${minutes}m ${seconds}s`;
}

function disableClosedDates() {
    const dateInput = document.getElementById('bookingDateInput');
    if (!dateInput || typeof closedDates === 'undefined') return;

    const today = new Date();
    const isoToday = today.toISOString().split('T')[0];
    dateInput.min = isoToday;

    dateInput.addEventListener('change', function () {
        if (closedDates.includes(this.value)) {
            alert('The salon is closed on the selected date. Please choose another date.');
            this.value = '';
        }
    });
}

setInterval(updateDigitalClock, 1000);
setInterval(updateClosingCountdown, 1000);
updateDigitalClock();
updateClosingCountdown();
disableClosedDates();
