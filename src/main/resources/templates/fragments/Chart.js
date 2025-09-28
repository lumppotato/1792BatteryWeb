//chatgpt needs to be modified

fetch(`/batteries/${batteryId}/tests/${testId}/data`)
    .then(res => res.json())
    .then(data => {
        const ctx = document.getElementById('chart').getContext('2d');
        new Chart(ctx, {
            type: 'line',
            data: {
                labels: labels,
                datasets: [
                    {
                        label: 'Voltage (V)',
                        data: voltageData,
                        borderColor: 'blue',
                        yAxisID: 'y',
                        tension: 0.1
                    },
                    {
                        label: 'Current (A)',
                        data: currentData,
                        borderColor: 'red',
                        yAxisID: 'y1',
                        tension: 0.1
                    }
                ]
            },
            options: {
                responsive: true,
                interaction: {
                    mode: 'index',
                    intersect: false,
                },
                stacked: false,
                plugins: {
                    title: {
                        display: true,
                        text: 'Battery Test Results'
                    }
                },
                scales: {
                    y: {
                        type: 'linear',
                        display: true,
                        position: 'left',
                        title: {
                            display: true,
                            text: 'Voltage (V)'
                        }
                    },
                    y1: {
                        type: 'linear',
                        display: true,
                        position: 'right',
                        title: {
                            display: true,
                            text: 'Current (A)'
                        },
                        grid: {
                            drawOnChartArea: false
                        }
                    }
                }
            }

        });
    });
