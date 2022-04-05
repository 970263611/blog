function backgroundInit(){
    let container;
    let camera, scene, projector, renderer;
    const PI2 = Math.PI * 2;
    let mouse = {x: 0, y: 0}, INTERSECTED;
    const radius = 600;
    let theta = 0;
    const programFill = function (context) {
        context.beginPath();
        context.arc(0, 0, 1, 0, PI2, true);
        context.closePath();
        context.fill();
    };
    const programStroke = function (context) {
        context.lineWidth = 0.05;
        context.beginPath();
        context.arc(0, 0, 1, 0, PI2, true);
        context.closePath();
        context.stroke();
    };
    init();
    animate();
    function init() {
        // container = document.createElement('div');
        // container.id = ('background')
        // container.style.position = 'flexed';
        container = document.getElementById('background')
        container.style.zIndex = '0';
        document.body.appendChild(container);
        camera = new THREE.PerspectiveCamera(70, window.innerWidth / window.innerHeight, 1, 10000);
        camera.position.set(0, 300, 500);
        scene = new THREE.Scene();
        for (let i = 0; i < 100; i++) {
            const particle = new THREE.Particle(new THREE.ParticleCanvasMaterial({
                color: Math.random() * 0x808080 + 0x808080,
                program: programStroke
            }));
            particle.position.x = Math.random() * 800 - 400;
            particle.position.y = Math.random() * 800 - 400;
            particle.position.z = Math.random() * 800 - 400;
            particle.scale.x = particle.scale.y = Math.random() * 10 + 10;
            scene.add(particle);
        }
        projector = new THREE.Projector();
        renderer = new THREE.CanvasRenderer();
        renderer.setSize(window.innerWidth, window.innerHeight);
        container.appendChild(renderer.domElement);
        document.addEventListener('mousemove', onDocumentMouseMove, false);
        window.addEventListener('resize', onWindowResize, false);
    }
    function onWindowResize() {
        camera.aspect = window.innerWidth / window.innerHeight;
        camera.updateProjectionMatrix();
        renderer.setSize(window.innerWidth, window.innerHeight);
    }
    function onDocumentMouseMove(event) {
        event.preventDefault();
        mouse.x = (event.clientX / window.innerWidth) * 2 - 1;
        mouse.y = -(event.clientY / window.innerHeight) * 2 + 1;
    }
    function animate() {
        requestAnimationFrame(animate);
        render();
    }
    function render() {
        // rotate camera
        theta += 0.2;
        camera.position.x = radius * Math.sin(theta * Math.PI / 360);
        camera.position.y = radius * Math.sin(theta * Math.PI / 360);
        camera.position.z = radius * Math.cos(theta * Math.PI / 360);
        camera.lookAt(scene.position);
        // find intersections
        camera.updateMatrixWorld();
        const vector = new THREE.Vector3(mouse.x, mouse.y, 0.5);
        projector.unprojectVector(vector, camera);
        const ray = new THREE.Ray(camera.position, vector.subSelf(camera.position).normalize());
        const intersects = ray.intersectObjects(scene.children);
        if (intersects.length > 0) {
            if (INTERSECTED != intersects[0].object) {
                if (INTERSECTED) INTERSECTED.material.program = programStroke;
                INTERSECTED = intersects[0].object;
                INTERSECTED.material.program = programFill;
            }
        } else {
            if (INTERSECTED) INTERSECTED.material.program = programStroke;
            INTERSECTED = null;
        }
        renderer.render(scene, camera);
    }
}