import React, { useEffect, useState } from "react";
import './loggedin.styles.scss';
import Side_menu from "../../Components/side-bar/sidebar.component";
import Header from "../../Components/Header/header.component";
import { MenuItemContext } from "../../contexts/menuitem.context";
const Loggedin = () => {
    //fetch Block
    const [loading, setLoading] = useState(true);
    const [projects, setProjects] = useState([]);
    const [selectedProjectIndex, setSelectedProjectIndex] = useState(null);
    const [showGraphs, setShowGraphs] = useState(false);

    const [projectSprints, setProjectSprints] = useState([]);
    const [disableShowGraphBtn, setDisableShowGraphBtn] = useState(true);

    useEffect(() => {
        fetch("http://localhost:8080/taiga/projects")
            .then((response) => response.json())
            .then((data) => {
                console.log(data)
                setProjects(data);
                setLoading(false);
            })
            .catch(error => {
                alert('Error in getting projects');
                setLoading(false);
            });
    }, [])

    function changeProject (e) {
        const index = e.target.value;

        console.log(index);
        setSelectedProjectIndex(index);
        setProject(projects[index].projectName);

        if (index) {
            setProjectSprints(projects[index].sprintName);
        }
    }

    function changeProjectSprint (e) {
        const index = e.target.value;


        if (index == 'Select sprint') {
            setSelectedSprintIndex(null);
            setDisableShowGraphBtn(true);
            setShowGraphs(false);
            return;
        }
        setSprint(index);
        setDisableShowGraphBtn(false);
        
        setSelectedSprintIndex(index);
    }

    if (loading) {
        return <div>
            <br />
            <h2 style={{ color: 'white'}}>Loading ... </h2>
        </div>
    }

    console.log(projects);
    return(
    <div className="loggedin">

        <Header/>

            <h1>Hello, welcome to the platform for demo. Please select a project to continue..</h1>
            <select className= "projectDropdown" onChange={changeProject} value={selectedProjectIndex}>
                <option>Select project</option>
                {projects.map((project, index) => <option value={index}  >
                    {project.projectName}
                </option>)}
            </select>


            {projectSprints.length > 0 && 
                <select className= "projectDropdown" onChange={changeProjectSprint} value={selectedSprintIndex} style={{ marginLeft: 20 }}>
                    <option>Select sprint</option>
                    {projectSprints.map((sprint, index) => <option value={sprint} >
                        {sprint}
                    </option>)}
                </select>
            }

        
            {projectSprints.length > 0
                && <button className = "btn " onClick={()=>setShowGraphs(true)} >
                    submit
                </button>}

        </div>
        
        {showGraphs &&
            <div>
                <Side_menu/>
                <BDGraphItem/> 
                <BDConsistensy/>
        </div>}


    </div>
    )
}
export default Loggedin;