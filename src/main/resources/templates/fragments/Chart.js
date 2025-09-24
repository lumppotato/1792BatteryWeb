//chatgpt needs to be modified

fetch(`/batteries/${batteryId}/tests/${testId}/data`)
    .then(res => res.json())
    .then(data => {
        const ctx = document.getElementById('chart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: data.map(d => d.time),
                datasets: [
                    {
                        label: 'Voltage (V)',
                        data: data.map(d => d.voltage),
                        borderColor: 'blue',
                        yAxisID: 'y1'
                    },
                    {
                        label: 'Current (A)',
                        data: data.map(d => d.current),
                        borderColor: 'red',
                        yAxisID: 'y2'
                    }
                ]
            },
            options: {
                scales: {
                    y1: { type: 'linear', position: 'left' },
                    y2: { type: 'linear', position: 'right' }
                }
            }
        });
    });
