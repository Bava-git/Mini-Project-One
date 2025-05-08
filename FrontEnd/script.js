const tablebody = document.getElementById('tablebody');
const baseURL = "http://localhost:3000/employee";

const fetchData = async () => {
    const MyArr = [];
    try {
        let response = await fetch(`${baseURL}`);
        if (!response.ok) {
            throw new Error(`Response Error in list of employee : ${response.status}`);
        }
        let data = await response.json();
        MyArr.push(...data);
    } catch (error) {
        console.error("Error in list of employee :", error);
    }


    let html = "";
    MyArr.forEach((Item, Index) => {
        html += `
                <tr>
                    <td>${Index + 1}</td>
                    <td>${Item.employee_id}</td>
                    <td><a href="#" onclick="viewEmployeeDetails('${Item.employee_id}');" popover-target="viewDiv" popover-trigger="click">${Item.employee_name}</a></td>
                    <td>${Item.employee_email}</td>
                    <td>${Item.employee_salary}</td>
                </tr>
            `;
    })
    tablebody.innerHTML = html;
};
fetchData();

const viewEmployeeDetails = async (id) => {

    let html = "";
    let data = "";
    document.getElementById('viewDiv').style.display = 'block';

    try {
        let response = await fetch(`${baseURL}/${id}`);
        if (!response.ok) {
            throw new Error(`Response Error in view employee : ${response.status}`);
        }
        data = await response.json();
    } catch (error) {
        console.error("Error in view employee :", error);
    }

    html = `
        <div class="formBns">
                <p>Employee ID</p>
                <p>${data.employee_id}</p>
            </div>
            <div class="formBns">
                <p>Employee Name</p>
                <p>${data.employee_name}</p>
            </div>
            <div class="formBns">
                <p>Employee Email</p>
                <p>${data.employee_email}</p>
            </div>
            <div class="formBns">
                <p>Employee Salary</p>
                <p>${data.employee_salary}</p>
            </div>
            <div class="formBns">
                <button class="addBn" id="cancelBn" onclick="hider();">Cancel</button>
                <button class="addBn" onclick="getreadyforupdatedata('${data.employee_id}');">Update</button>
                <button class="addBn" onclick="deleteEmployee('${data.employee_id}');">Delete</button>
            </div>
        </div>
            `;
    document.getElementById('displayDetails').innerHTML = html;
}

const deleteEmployee = async (id) => {
    try {
        let response = await fetch(`${baseURL}/delete/${id}`, {
            method: "DELETE"
        })
        if (!response.ok) {
            throw new Error(`Response Error in delete employee : ${response.status}`);
        } else {
            toaster("Employee deleted successfully!", "red");
            console.log(await response.text());

        }
    } catch (error) {
        console.log("Error in delete employee : " + error);
    }

    document.getElementById('viewDiv').style.display = 'none';
    fetchData();
}

const getreadyforupdatedata = async (id) => {

    let html = "";
    let data = "";
    try {
        let response = await fetch(`${baseURL}/${id}`);
        if (!response.ok) {
            throw new Error(`Response Error in get an employee for update : ${response.status}`);
        }
        data = await response.json();
        // console.log(data);
    } catch (error) {
        console.error("Error in get an employee for update :", error);
    }

    html = `
        <div class="formBns">
                <p>Employee ID</p>
                <input type="text" id="udpate_employee_id" autocomplete="off"  value=${data.employee_id} disabled >
            </div>
            <div class="formBns">
                <p>Employee Name</p>
                <input type="text" id="udpate_employee_name" autocomplete="off" value=${data.employee_name}>
            </div>
            <div class="formBns">
                <p>Employee Name</p>
                <input type="email" id="udpate_employee_email" autocomplete="off" value=${data.employee_email}>
            </div>
            <div class="formBns">
                <p>Employee Email</p>
                <input type="text" id="udpate_employee_salary" autocomplete="off" value=${data.employee_salary}>
            </div>
            <div class="formBns">
                <button class="addBn" id="cancelBn" onclick="hider();">Cancel</button>
                <button class="addBn" onclick="updateEmployee('${id}');">Update</button>
            </div>
        </div>
            `;
    document.getElementById('displayDetails').innerHTML = html;
}

const updateEmployee = async (id) => {

    let employee_id = id;
    let employee_name = document.getElementById('udpate_employee_name').value;
    let employee_email = document.getElementById('udpate_employee_email').value;
    let employee_salary = document.getElementById('udpate_employee_salary').value;

    if (!employee_name || !employee_email || !employee_salary) {
        alert("Check the value");
        return;
    }

    sendData = { employee_id, employee_name, employee_email, employee_salary };

    try {
        let response = await fetch(`${baseURL}/update/${id}`, {
            method: "PUT",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify(sendData)
        })
        if (!response.ok) {
            throw new Error(`Response Error in update enmployee : ${response.status}`);
        } else {
            toaster("Employee updated successfully!", "green");
            console.log(await response.text());
        }

    } catch (error) {
        console.log("Error in update enmployee  : " + error);
    }

    document.getElementById('viewDiv').style.display = 'none';
    fetchData();
}

let randomid;
const randomnumber = async () => {

    let isUniqueEmployeeId = false;

    while (!isUniqueEmployeeId) {
        randomid = "EM" + Math.floor(1000 + Math.random() * 9000);
        try {
            let response = await fetch(`${baseURL}/${randomid}`);
            if (response.status === 404) {
                isUniqueEmployeeId = true;
            }
        } catch (error) {
            console.error("Error in crosscheck employee id :", error);
        }
    }
}

document.getElementById('insertBn').addEventListener('click', async (event) => {
    event.preventDefault();

    document.querySelectorAll(".addEmployee").forEach(e => {
        if (e.value === "") {
            e.classList.add("red");
        } else {
            e.classList.remove("red");
        }
    });

    if (document.querySelectorAll(".addEmployee.red").length > 0) {
        document.getElementById('formDiv').style.display = 'block';
        toaster('Invaild data', "red");
        return;
    }

    randomnumber();
    let employee_id = randomid;
    let employee_name = document.getElementById('employee_name').value;

    if (employee_name.length < 3 || employee_name.length > 30) {
        toaster('Name should have min 3 charactors to max 30 charactors', "red");
        document.getElementById('formDiv').style.display = 'block';
        return;
    }

    let employee_email = document.getElementById('employee_email').value;

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
    if (!emailRegex.test(employee_email)) {
        toaster('Please enter a valid email address!', "red");
        document.getElementById('formDiv').style.display = 'block';
        return;
    } else {
        try {
            let response = await fetch(`${baseURL}/search/${employee_email}`);
            if (response.status === 200) {
                toaster('Email Id already available', "red");
                document.getElementById('formDiv').style.display = 'block';
                return;
            }
        } catch (error) {
            console.error("Error in crosscheck employee id :", error);
        }
    }

    let employee_salary = document.getElementById('employee_salary').value;

    if (employee_salary < 999 || employee_salary >= 100000) {
        toaster('Salary should have min 1000 to max 100000', "red");
        document.getElementById('formDiv').style.display = 'block';
        return;
    }

    sendData = { employee_id, employee_name, employee_email, employee_salary };

    try {
        let response = await fetch(`${baseURL}/add`, {
            method: "POST",
            headers: {
                "content-type": "application/json"
            },
            body: JSON.stringify(sendData)
        })
        if (!response.ok) {
            throw new Error(`Response Error in Create new employee: ${response.status}`);
        } else {
            toaster("Employee added successfully!", "green");
        }
    } catch (error) {
        console.log("Error in Create new employee : " + error);
    }

    fetchData();
    resetForm();
});


const hider = () => {
    document.getElementById('formDiv').style.display = 'none';
    document.getElementById('viewDiv').style.display = 'none';
}

document.getElementById('addEmployee').addEventListener('click', () => {
    document.getElementById('formDiv').style.display = 'block';
});


const resetForm = () => {
    const form = document.getElementById("myForm");
    form.reset();
};

document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
        hider();
    }
});


const toaster = (message, color) => {
    // Show Toast Notification
    const toast = document.getElementById('toast');
    toast.innerHTML = message;
    toast.classList.add(color);

    // Hide Toast After 3 Seconds
    setTimeout(() => {
        toast.classList.remove(color);
    }, 3000);
}