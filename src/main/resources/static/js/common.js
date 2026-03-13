function toggleTask(button, taskId) {
	const url = `/completed?taskId=${taskId}`;
	const csrfToken = document.querySelector('input[name="_csrf"]')?.value;
	fetch(url, {
		method: 'POST',
		headers: { 'X-CSRF-TOKEN': csrfToken }
	})
		.then(response => {
			if (response.ok) {
				button.classList.toggle('is-done');
				const completedErement = document.getElementById('completed-count');
				let currentCount = parseInt(completedErement.innerText);
				if (button.classList.contains('is-done')) {
					completedErement.innerText = currentCount + 1;
				} else {
					completedErement.innerText = currentCount - 1;
				}
				console.log("状態を更新しました");
			}
		})
		.catch(error => console.error("通信エラー:", error));
}

function updateTask(textarea) {
	const form = textarea.form;
	const formData = new FormData(form);
	const url = "/update";

	fetch(url, {
		method: 'POST',
		body: formData
	})
		.then(response => {
			if (response.ok) {
				console.log("保存に成功しました");
			}
		})
		.catch(error => console.error("エラー:", error));
}