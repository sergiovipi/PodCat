window.onload = function() {
    let capturaAudio=document.getElementById("audio").innerHTML;
    let srcAudio = "/audios/"+ capturaAudio;
    let playing = false;
    let audio = document.getElementById("audio");
    let contenidorAnimacio = document.getElementById("contenidor-animacio");
    let boto = document.getElementById("play");
    let timeline = document.getElementById("timeline");
    let progress = document.createElement("div");
    let currentTimeSpan = document.getElementById("current-time");
    let totalTimeSpan = document.getElementById("total-time");

    let animacio = lottie.loadAnimation({
        container: contenidorAnimacio,
        renderer: 'svg',
        loop: false,
        autoplay: false,
        path: '/json/toPause.json'
    })

    audio.setAttribute("src", srcAudio);
    progress.classList.add("progress");
    timeline.appendChild(progress);

    boto.addEventListener('click', play);

    audio.addEventListener('timeupdate', function() {
        let percent = (audio.currentTime / audio.duration) * 100;
        progress.style.width = percent + '%';
        currentTimeSpan.innerText = formatTime(audio.currentTime) + " / ";
    });

    audio.addEventListener('loadedmetadata', function() {
        currentTimeSpan.innerText = formatTime(audio.currentTime) + " / ";
        totalTimeSpan.innerText = formatTime(audio.duration);
    });

    timeline.addEventListener('click', function(event) {
        let width = this.offsetWidth;
        let clickX = event.offsetX;
        let duration = audio.duration;

        audio.currentTime = (clickX / width) * duration;
    });

    function play() {
        if (!playing) {
            audio.play();
            animacio.setDirection(1);
            animacio.play();
            playing = true;
        } else {
            audio.pause();
            animacio.setDirection(-1);
            animacio.play();
            playing = false;
        }
    }

    function formatTime(seconds) {
        let date = new Date(null);
        date.setSeconds(seconds);
        return date.toISOString().substr(11, 8);
    }

    //ona d'audio
        let context = new AudioContext();
        let src = context.createMediaElementSource(audio);
        let analyser = context.createAnalyser();

        let content = document.getElementById("content");
        let canvas = document.getElementById("canvas");
        let ctx = canvas.getContext("2d");

        let width = content.clientWidth;
        let height = content.clientHeight;
        canvas.width = width;
        canvas.height = height;
        canvas.style.width = width + 'px';
        canvas.style.height = height + 'px';
        canvas.style.margin = "0 auto";

        src.connect(analyser);
        analyser.connect(context.destination);

        analyser.fftSize = 512;

        let bufferLength = analyser.frequencyBinCount;
        console.log(bufferLength);

        let dataArray = new Uint8Array(bufferLength);


        let barWidth = (canvas.width / bufferLength) * 2.5;
        let barHeight;
        let x = 0;

        function renderFrame() {
            requestAnimationFrame(renderFrame);

            x = 0;

            analyser.getByteFrequencyData(dataArray);

            //ctx.fillStyle = "rgb(255, 255, 255,0.5)";
            ctx.fillStyle = "rgb(0, 0, 0,0.5)";
            ctx.fillRect(0, 0, canvas.width, canvas.height);

            for (let i = 0; i < bufferLength; i++) {
                barHeight = dataArray[i];

                ctx.fillStyle = "rgb(191, 148, 29, 0.5)";
                ctx.fillRect(x, canvas.height - barHeight, barWidth, barHeight);

                x += barWidth + 2;
            }
        }

        renderFrame();
};