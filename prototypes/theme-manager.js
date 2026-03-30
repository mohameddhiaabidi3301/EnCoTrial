// ─── ENICAR Connect — Theme Manager & Animation Engine ───────────────────────
// Applies theme before DOM renders to prevent flash.
(function () {
    var saved = localStorage.getItem('enicar-theme') || 'dark';
    document.documentElement.setAttribute('data-theme', saved);
})();

// ─── Theme Toggle ─────────────────────────────────────────────────────────────
window.toggleTheme = function () {
    var current = document.documentElement.getAttribute('data-theme');
    var next = current === 'dark' ? 'light' : 'dark';
    document.documentElement.setAttribute('data-theme', next);
    localStorage.setItem('enicar-theme', next);
    var icon = document.querySelector('.theme-toggle-icon');
    if (icon) icon.className = 'theme-toggle-icon fas ' + (next === 'dark' ? 'fa-sun' : 'fa-moon');
    // Update particle color
    if (window._particleEngine) window._particleEngine.updateTheme(next);
};

// ─── Particle Network Engine ──────────────────────────────────────────────────
window.initParticles = function (canvasId) {
    var canvas = document.getElementById(canvasId);
    if (!canvas) return;
    var ctx = canvas.getContext('2d');

    function resize() {
        canvas.width = window.innerWidth;
        canvas.height = window.innerHeight;
    }
    resize();
    window.addEventListener('resize', resize);

    var NUM = 55;
    var CONNECT_DIST = 140;
    var particles = [];

    function getColor() {
        var theme = document.documentElement.getAttribute('data-theme');
        return theme === 'light' ? '13, 31, 60' : '201, 168, 76';
    }

    function Particle() {
        this.reset();
    }
    Particle.prototype.reset = function () {
        this.x = Math.random() * canvas.width;
        this.y = Math.random() * canvas.height;
        this.vx = (Math.random() - 0.5) * 0.5;
        this.vy = (Math.random() - 0.5) * 0.5;
        this.r = Math.random() * 1.8 + 0.8;
    };
    Particle.prototype.update = function () {
        this.x += this.vx;
        this.y += this.vy;
        if (this.x < 0 || this.x > canvas.width) this.vx *= -1;
        if (this.y < 0 || this.y > canvas.height) this.vy *= -1;
    };

    for (var i = 0; i < NUM; i++) particles.push(new Particle());

    function draw() {
        ctx.clearRect(0, 0, canvas.width, canvas.height);
        var col = getColor();
        for (var a = 0; a < particles.length; a++) {
            particles[a].update();
            var p = particles[a];
            ctx.beginPath();
            ctx.arc(p.x, p.y, p.r, 0, Math.PI * 2);
            ctx.fillStyle = 'rgba(' + col + ', 0.45)';
            ctx.fill();
            for (var b = a + 1; b < particles.length; b++) {
                var q = particles[b];
                var dx = p.x - q.x, dy = p.y - q.y;
                var dist = Math.sqrt(dx * dx + dy * dy);
                if (dist < CONNECT_DIST) {
                    ctx.beginPath();
                    ctx.moveTo(p.x, p.y);
                    ctx.lineTo(q.x, q.y);
                    var alpha = (1 - dist / CONNECT_DIST) * 0.18;
                    ctx.strokeStyle = 'rgba(' + col + ', ' + alpha + ')';
                    ctx.lineWidth = 0.8;
                    ctx.stroke();
                }
            }
        }
        requestAnimationFrame(draw);
    }
    draw();

    window._particleEngine = {
        updateTheme: function () { /* color is read live */ }
    };
};

// ─── Staggered card entry animation ──────────────────────────────────────────
document.addEventListener('DOMContentLoaded', function () {
    // Set icon
    var theme = document.documentElement.getAttribute('data-theme');
    var icon = document.querySelector('.theme-toggle-icon');
    if (icon) icon.className = 'theme-toggle-icon fas ' + (theme === 'dark' ? 'fa-sun' : 'fa-moon');

    // Animate cards
    var cards = document.querySelectorAll('.card, .job-card, .group-card, .res-card, .section-card, .profile-header, .login-card');
    cards.forEach(function (el, i) {
        el.style.opacity = '0';
        el.style.transform = 'translateY(22px)';
        el.style.transition = 'opacity .5s ease ' + (i * 0.08) + 's, transform .5s ease ' + (i * 0.08) + 's';
        setTimeout(function () {
            el.style.opacity = '1';
            el.style.transform = 'translateY(0)';
        }, 60);
    });

    // Init particles if canvas present
    initParticles('particles-canvas');
});
